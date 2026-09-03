package micapolos.awt;

import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class MultiplyComposite implements Composite {
  public static final MultiplyComposite INSTANCE = new MultiplyComposite();

  private MultiplyComposite() {
  }

  @Override
  public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
    return new MultiplyContext();
  }

  private static class MultiplyContext implements CompositeContext {
    @Override
    public void dispose() {
    }

    @Override
    public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
      int width = Math.min(src.getWidth(), dstIn.getWidth());
      int height = Math.min(src.getHeight(), dstIn.getHeight());

      int[] srcPixels = new int[width * 4];
      int[] dstPixels = new int[width * 4];

      for (int y = 0; y < height; y++) {
        src.getPixels(0, y, width, 1, srcPixels);
        dstIn.getPixels(0, y, width, 1, dstPixels);

        for (int x = 0; x < width * 4; x += 4) {
          int sr = srcPixels[x];
          int sg = srcPixels[x + 1];
          int sb = srcPixels[x + 2];
          int sa = srcPixels[x + 3];

          int dr = dstPixels[x];
          int dg = dstPixels[x + 1];
          int db = dstPixels[x + 2];
          int da = dstPixels[x + 3];

          if (sa == 0) continue;

          dstPixels[x] = blendChannel(sr, dr, sa);
          dstPixels[x + 1] = blendChannel(sg, dg, sa);
          dstPixels[x + 2] = blendChannel(sb, db, sa);
          dstPixels[x + 3] = Math.min(255, da + sa - (da * sa / 255));
        }

        dstOut.setPixels(0, y, width, 1, dstPixels);
      }
    }

    private int blendChannel(int s, int d, int sa) {
      double srcAlpha = sa / 255.0;
      // Multiply formula: (S * B)
      double multiply = (s * d) / 255.0;

      // Linear interpolate between original destination color and multiplied color based on source alpha
      double result = d * (1.0 - srcAlpha) + multiply * srcAlpha;

      return (int) Math.min(255, Math.max(0, result));
    }
  }
}