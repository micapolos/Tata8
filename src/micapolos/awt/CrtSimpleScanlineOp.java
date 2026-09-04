package micapolos.awt;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferInt;

public class CrtSimpleScanlineOp implements BufferedImageOp {

  private final float scanlineDarkness; // 0.0f (none) to 1.0f (black lines)
  private final int bloomThreshold;     // Brightness cutoff 0-255 (e.g. 180)
  private final float bloomBoost;       // Intensity boost for light parts (e.g. 0.25f)

  public CrtSimpleScanlineOp(float scanlineDarkness, int bloomThreshold, float bloomBoost) {
    this.scanlineDarkness = Math.max(0.0f, Math.min(1.0f, scanlineDarkness));
    this.bloomThreshold = bloomThreshold;
    this.bloomBoost = Math.max(0.0f, bloomBoost);
  }

  public CrtSimpleScanlineOp() {
    this(0.40f, 180, 0.25f); // Balanced default settings
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

        // 1. Calculate perceived brightness: (R*2 + G*5 + B) / 8
        int luminance = (r * 2 + g * 5 + b) >> 3;

        // 2. Apply bloom glow to light parts above threshold
        if (luminance > bloomThreshold) {
          float factor = 1.0f + (((float) (luminance - bloomThreshold) / (255 - bloomThreshold)) * bloomBoost);
          r = Math.min(255, (int) (r * factor));
          g = Math.min(255, (int) (g * factor));
          b = Math.min(255, (int) (b * factor));
        }

        int basePixel = (a << 24) | (r << 16) | (g << 8) | b;

        // 3. Scanline dimming (bright pixels punch through scanlines slightly)
        float activeScanDarkness = scanlineDarkness * (1.0f - ((float) luminance / 512.0f));
        float scanFactor = 1.0f - activeScanDarkness;

        int sr = (int) (r * scanFactor);
        int sg = (int) (g * scanFactor);
        int sb = (int) (b * scanFactor);
        int scanlinePixel = (a << 24) | (sr << 16) | (sg << 8) | sb;

        // 4. Map to 3x3 block (2 rows base color, 1 row scanline)
        int outX = x * 3;
        int outY = y * 3;

        // Rows 0 & 1: Crisp scaled color
        for (int subY = 0; subY < 2; subY++) {
          int rowOffset = (outY + subY) * dstW + outX;
          dstData[rowOffset]     = basePixel;
          dstData[rowOffset + 1] = basePixel;
          dstData[rowOffset + 2] = basePixel;
        }

        // Row 2: Scanline
        int scanOffset = (outY + 2) * dstW + outX;
        dstData[scanOffset]     = scanlinePixel;
        dstData[scanOffset + 1] = scanlinePixel;
        dstData[scanOffset + 2] = scanlinePixel;
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