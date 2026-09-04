package micapolos.awt;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferInt;

public class CrtPhosphorMatrixOp implements BufferedImageOp {

  // Sub-pixel intensity matrix (3x3 grid for rounded organic pixel spots)
  private static final float[][] PHOSPHOR_WEIGHTS = {
    { 0.80f, 1.00f, 0.80f },  // Top row
    { 0.90f, 1.10f, 0.90f },  // Middle core (bright center)
    { 0.35f, 0.45f, 0.35f }   // Bottom scanline gap (curved)
  };

  private static final float BRIGHTNESS_NORMALIZER = 1.25f;

  private final int bloomThreshold; // Light cutoff (0-255, e.g., 180)
  private final float bloomBoost;   // Glow intensity for light parts (e.g., 0.25f)

  public CrtPhosphorMatrixOp(int bloomThreshold, float bloomBoost) {
    this.bloomThreshold = bloomThreshold;
    this.bloomBoost = Math.max(0.0f, bloomBoost);
  }

  public CrtPhosphorMatrixOp() {
    this(180, 0.25f);
  }

  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {
    if (dest == null) {
      dest = createCompatibleDestImage(src, null);
    }

    int srcW = src.getWidth();
    int srcH = src.getHeight();
    int dstW = dest.getWidth();

    int[] srcData = ((DataBufferInt) src.getRaster().getDataBuffer()).getData();
    int[] dstData = ((DataBufferInt) dest.getRaster().getDataBuffer()).getData();

    for (int y = 0; y < srcH; y++) {
      for (int x = 0; x < srcW; x++) {
        int argb = srcData[y * srcW + x];

        int a = (argb >> 24) & 0xFF;
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;

        // 1. Calculate perceived brightness
        int luminance = (r * 2 + g * 5 + b) >> 3;

        // 2. Apply bloom boost to bright areas
        if (luminance > bloomThreshold) {
          float factor = 1.0f + (((float) (luminance - bloomThreshold) / (255 - bloomThreshold)) * bloomBoost);
          r = Math.min(255, (int) (r * factor));
          g = Math.min(255, (int) (g * factor));
          b = Math.min(255, (int) (b * factor));
        }

        // Brightness punch-through factor (bright pixels fill the scanline gap more)
        float scanPunch = (luminance / 512.0f);

        // 3. Map to 3x3 matrix with per-subpixel weights
        int outX = x * 3;
        int outY = y * 3;

        for (int subY = 0; subY < 3; subY++) {
          int rowOffset = (outY + subY) * dstW + outX;

          for (int subX = 0; subX < 3; subX++) {
            float weight = PHOSPHOR_WEIGHTS[subY][subX];

            // Soften scanline darkeners on bright pixels
            if (subY == 2) {
              weight += scanPunch * 0.20f;
            }

            // Apply global energy normalization factor
            float normalizedWeight = weight * BRIGHTNESS_NORMALIZER;

            int pr = (int) ((float) r * normalizedWeight);
            int pg = (int) ((float) g * normalizedWeight);
            int pb = (int) ((float) b * normalizedWeight);

            if (pr > 255) {
              pr = 255;
            }
            if (pg > 255) {
              pg = 255;
            }
            if (pb > 255) {
              pb = 255;
            }

            dstData[rowOffset + subX] = (a << 24) | (pr << 16) | (pg << 8) | pb;
          }
        }
      }
    }

    return dest;
  }

  @Override
  public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM) {
    if (destCM == null) destCM = src.getColorModel();
    return new BufferedImage(
      destCM,
      destCM.createCompatibleWritableRaster(src.getWidth() * 3, src.getHeight() * 3),
      destCM.isAlphaPremultiplied(),
      null
    );
  }

  @Override
  public Rectangle2D getBounds2D(BufferedImage src) {
    return new Rectangle2D.Float(0, 0, src.getWidth() * 3, src.getHeight() * 3);
  }

  @Override
  public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
    if (dstPt == null) dstPt = new Point2D.Float();
    dstPt.setLocation(srcPt.getX() * 3, srcPt.getY() * 3);
    return dstPt;
  }

  @Override
  public RenderingHints getRenderingHints() { return null; }
}