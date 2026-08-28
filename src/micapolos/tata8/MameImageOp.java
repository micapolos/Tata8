package micapolos.tata8;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.DataBufferInt;
import java.lang.Math;

public final class MameImageOp implements BufferedImageOp {
  private static final int SCALE = 3;

  // Configurable MAME HLSL Parameters
  private final float bloomIntensity = 0.25f; // Amount of phosphor bleed into neighbors
  private final float scanlineDepth  = 0.35f; // Scanline gap darkness (0.0 = none, 1.0 = pitch black)
  private final float gammaCorrection = 1.35f; // Color boost to counteract subpixel mask darkening

  // Precomputed Look-Up Tables
  private final int[] gammaLUT = new int[256];
  private final int[] scanlineLUT = new int[3]; // 3x vertical interpolation coefficients

  public MameImageOp() {
    // Precompute Gamma Correction LUT
    for (int i = 0; i < 256; i++) {
      float normalized = i / 255.0f;
      float boosted = (float) Math.pow(normalized, 1.0f / gammaCorrection) * 255.0f;
      gammaLUT[i] = Math.min(255, Math.max(0, (int) boosted));
    }

    // Precompute Scanline Profile across 3 destination rows per source pixel
    // Row 0 & 1 are main beam spot; Row 2 is beam transition / scanline gap
    scanlineLUT[0] = (int) ((1.0f - (scanlineDepth * 0.1f)) * 256);
    scanlineLUT[1] = (int) ((1.0f - (scanlineDepth * 0.0f)) * 256);
    scanlineLUT[2] = (int) ((1.0f - (scanlineDepth * 0.85f)) * 256);
  }

  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {
    if (dest == null) {
      dest = createCompatibleDestImage(src, src.getColorModel());
    }

    int srcW = src.getWidth();
    int srcH = src.getHeight();
    int dstW = dest.getWidth();

    int[] srcPixels = ((DataBufferInt) src.getRaster().getDataBuffer()).getData();
    int[] dstPixels = ((DataBufferInt) dest.getRaster().getDataBuffer()).getData();

    for (int y = 0; y < srcH; y++) {
      int srcRow = y * srcW;
      int ym1 = (y > 0) ? (y - 1) * srcW : srcRow;
      int yp1 = (y < srcH - 1) ? (y + 1) * srcW : srcRow;

      int dstRow0 = (y * SCALE) * dstW;
      int dstRow1 = dstRow0 + dstW;
      int dstRow2 = dstRow1 + dstW;

      for (int x = 0; x < srcW; x++) {
        int xm1 = (x > 0) ? x - 1 : x;
        int xp1 = (x < srcW - 1) ? x + 1 : x;

        // Step 1: 4-Tap Cross Bloom Sampling around current pixel
        int centerRGB = srcPixels[srcRow + x];
        int leftRGB   = srcPixels[srcRow + xm1];
        int rightRGB  = srcPixels[srcRow + xp1];
        int topRGB    = srcPixels[ym1 + x];
        int bottomRGB = srcPixels[yp1 + x];

        // Unpack and apply Gamma Boost
        int cr = gammaLUT[(centerRGB >> 16) & 0xFF];
        int cg = gammaLUT[(centerRGB >> 8) & 0xFF];
        int cb = gammaLUT[centerRGB & 0xFF];

        // Average surrounding neighbors for phosphor bloom approximation
        int nSumR = gammaLUT[(leftRGB >> 16) & 0xFF] + gammaLUT[(rightRGB >> 16) & 0xFF] + gammaLUT[(topRGB >> 16) & 0xFF] + gammaLUT[(bottomRGB >> 16) & 0xFF];
        int nSumG = gammaLUT[(leftRGB >> 8) & 0xFF]  + gammaLUT[(rightRGB >> 8) & 0xFF]  + gammaLUT[(topRGB >> 8) & 0xFF]  + gammaLUT[(bottomRGB >> 8) & 0xFF];
        int nSumB = gammaLUT[leftRGB & 0xFF]         + gammaLUT[rightRGB & 0xFF]        + gammaLUT[topRGB & 0xFF]         + gammaLUT[bottomRGB & 0xFF];

        // Combine Center + Bloom Weighting
        int r = Math.min(255, (int) (cr * (1.0f - bloomIntensity) + (nSumR * 0.25f) * bloomIntensity));
        int g = Math.min(255, (int) (cg * (1.0f - bloomIntensity) + (nSumG * 0.25f) * bloomIntensity));
        int b = Math.min(255, (int) (cb * (1.0f - bloomIntensity) + (nSumB * 0.25f) * bloomIntensity));

        // Step 2: MAME Subpixel Aperture Masking (Triad setup)
        // Col 0: Red, Col 1: Green, Col 2: Blue
        int redPixel   = (r << 16) | (((g * 40) >> 8) << 8);
        int greenPixel = (((r * 40) >> 8) << 16) | (g << 8) | ((b * 40) >> 8);
        int bluePixel  = (((g * 40) >> 8) << 8) | b;

        int dstX = x * SCALE;

        // Step 3: Scanline Weighting across the 3 destination sub-rows
        int scanWeight0 = scanlineLUT[0];
        int scanWeight1 = scanlineLUT[1];
        int scanWeight2 = scanlineLUT[2];

        // Write Row 0
        dstPixels[dstRow0 + dstX]     = modulateColor(redPixel, scanWeight0);
        dstPixels[dstRow0 + dstX + 1] = modulateColor(greenPixel, scanWeight0);
        dstPixels[dstRow0 + dstX + 2] = modulateColor(bluePixel, scanWeight0);

        // Write Row 1
        dstPixels[dstRow1 + dstX]     = modulateColor(redPixel, scanWeight1);
        dstPixels[dstRow1 + dstX + 1] = modulateColor(greenPixel, scanWeight1);
        dstPixels[dstRow1 + dstX + 2] = modulateColor(bluePixel, scanWeight1);

        // Write Row 2 (Scanline Gap)
        dstPixels[dstRow2 + dstX]     = modulateColor(redPixel, scanWeight2);
        dstPixels[dstRow2 + dstX + 1] = modulateColor(greenPixel, scanWeight2);
        dstPixels[dstRow2 + dstX + 2] = modulateColor(bluePixel, scanWeight2);
      }
    }
    return dest;
  }

  private static int modulateColor(int rgb, int scaleFixedPoint) {
    int r = (((rgb >> 16) & 0xFF) * scaleFixedPoint) >> 8;
    int g = (((rgb >> 8) & 0xFF) * scaleFixedPoint) >> 8;
    int b = ((rgb & 0xFF) * scaleFixedPoint) >> 8;
    return (r << 16) | (g << 8) | b;
  }

  @Override
  public Rectangle2D getBounds2D(BufferedImage src) {
    return new Rectangle2D.Float(0, 0, src.getWidth() * SCALE, src.getHeight() * SCALE);
  }

  @Override
  public BufferedImage createCompatibleDestImage(BufferedImage src, java.awt.image.ColorModel destCM) {
    return new BufferedImage(src.getWidth() * SCALE, src.getHeight() * SCALE, BufferedImage.TYPE_INT_RGB);
  }

  @Override
  public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
    if (dstPt == null) {
      dstPt = new Point2D.Float();
    }
    dstPt.setLocation(srcPt.getX() * SCALE, srcPt.getY() * SCALE);
    return dstPt;
  }

  @Override
  public RenderingHints getRenderingHints() {
    return null;
  }
}