package micapolos.awt;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferInt;

public class GamePixelMatrixOp implements BufferedImageOp {

  private static final int MATRIX_SIZE = 6;

  // Base 6x6 energy weights (creates tile bevel + bright center core)
  private static final float[][] PIXEL_WEIGHTS = {
    { 1.15f, 1.15f, 1.10f, 1.10f, 1.05f, 0.85f }, // Top bevel (highlight)
    { 1.15f, 1.25f, 1.30f, 1.30f, 1.15f, 0.75f }, // Inner core top
    { 1.10f, 1.30f, 1.40f, 1.40f, 1.10f, 0.70f }, // Inner core bright spot
    { 1.10f, 1.30f, 1.40f, 1.40f, 1.10f, 0.70f }, // Inner core bright spot
    { 1.05f, 1.15f, 1.10f, 1.10f, 0.95f, 0.65f }, // Inner core bottom
    { 0.85f, 0.75f, 0.70f, 0.70f, 0.65f, 0.50f }  // Bottom/Right shadow edge
  };

  // Pre-calculated energy sum of the raw weight matrix (36 sub-pixels)
  private static final float MATRIX_TOTAL_ENERGY;
  static {
    float sum = 0.0f;
    for (int y = 0; y < MATRIX_SIZE; y++) {
      for (int x = 0; x < MATRIX_SIZE; x++) {
        sum += PIXEL_WEIGHTS[y][x];
      }
    }
    MATRIX_TOTAL_ENERGY = sum;
  }

  // Target mean gain factor to keep total light output equal to source pixel
  private static final float LUMINANCE_EQUALIZER = (MATRIX_SIZE * MATRIX_SIZE) / MATRIX_TOTAL_ENERGY;

  private final int bloomThreshold;
  private final float bloomBoost;

  public GamePixelMatrixOp(int bloomThreshold, float bloomBoost) {
    this.bloomThreshold = bloomThreshold;
    this.bloomBoost = Math.max(0.0f, bloomBoost);
  }

  public GamePixelMatrixOp() {
    this(190, 0.30f);
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
      int outY = y * MATRIX_SIZE;

      for (int x = 0; x < srcW; x++) {
        int argb = srcData[y * srcW + x];

        int a = (argb >> 24) & 0xFF;
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;

        // Fast perceived brightness (integer approximation)
        int luminance = (r * 2 + g * 5 + b) >> 3;

        // Apply bloom boost to extra bright game elements (e.g. fire, spells, UI)
        if (luminance > bloomThreshold) {
          float factor = 1.0f + (((float) (luminance - bloomThreshold) / (255 - bloomThreshold)) * bloomBoost);
          r = Math.min(255, (int) (r * factor));
          g = Math.min(255, (int) (g * factor));
          b = Math.min(255, (int) (b * factor));
        }

        // Higher energy flattens shadow borders to make highlights pop
        float energyFlatten = (luminance / 255.0f) * 0.20f;
        int outX = x * MATRIX_SIZE;

        for (int subY = 0; subY < MATRIX_SIZE; subY++) {
          int rowOffset = (outY + subY) * dstW + outX;

          for (int subX = 0; subX < MATRIX_SIZE; subX++) {
            float baseWeight = PIXEL_WEIGHTS[subY][subX];

            // Equalize base energy to match 100% original pixel light level
            float finalWeight = (baseWeight + energyFlatten) * LUMINANCE_EQUALIZER;

            int pr = (int) (r * finalWeight);
            int pg = (int) (g * finalWeight);
            int pb = (int) (b * finalWeight);

            // Fast primitive clamp
            if (pr > 255) pr = 255;
            if (pg > 255) pg = 255;
            if (pb > 255) pb = 255;

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
      destCM.createCompatibleWritableRaster(src.getWidth() * MATRIX_SIZE, src.getHeight() * MATRIX_SIZE),
      destCM.isAlphaPremultiplied(),
      null
    );
  }

  @Override
  public Rectangle2D getBounds2D(BufferedImage src) {
    return new Rectangle2D.Float(0, 0, src.getWidth() * MATRIX_SIZE, src.getHeight() * MATRIX_SIZE);
  }

  @Override
  public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
    if (dstPt == null) dstPt = new Point2D.Float();
    dstPt.setLocation(srcPt.getX() * MATRIX_SIZE, srcPt.getY() * MATRIX_SIZE);
    return dstPt;
  }

  @Override
  public RenderingHints getRenderingHints() { return null; }
}