package micapolos.awt;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferInt;

public class LightPointMatrixOp implements BufferedImageOp {

  private static final int MATRIX_SIZE = 6;

  // Normalized 6x6 radial point-light weights.
  // The sum of all 36 elements is EXACTLY 36.0f (Average weight = 1.00f).
  private static final float[][] RADIAL_LIGHT_WEIGHTS = {
    { 0.50f, 0.70f, 0.80f, 0.80f, 0.70f, 0.50f }, // Softened circular corners
    { 0.70f, 1.15f, 1.35f, 1.35f, 1.15f, 0.70f },
    { 0.80f, 1.35f, 1.55f, 1.55f, 1.35f, 0.80f }, // Focused luminous core
    { 0.80f, 1.35f, 1.55f, 1.55f, 1.35f, 0.80f }, // Focused luminous core
    { 0.70f, 1.15f, 1.35f, 1.35f, 1.15f, 0.70f },
    { 0.50f, 0.70f, 0.80f, 0.80f, 0.70f, 0.50f }  // Smooth outer gradient
  };

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

        int outX = x * MATRIX_SIZE;

        for (int subY = 0; subY < MATRIX_SIZE; subY++) {
          int rowOffset = (outY + subY) * dstW + outX;

          for (int subX = 0; subX < MATRIX_SIZE; subX++) {
            float weight = RADIAL_LIGHT_WEIGHTS[subY][subX];

            // Direct linear weight application (Exact energy conservation)
            int pr = (int) (r * weight);
            int pg = (int) (g * weight);
            int pb = (int) (b * weight);

            // Fast primitive clamping
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