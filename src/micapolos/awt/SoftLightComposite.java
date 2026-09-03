package micapolos.awt;

import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.lang.Math;

public class SoftLightComposite implements Composite {
  public static final SoftLightComposite INSTANCE = new SoftLightComposite();

  private SoftLightComposite() {
  }

  @Override
  public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
    return new SoftLightContext();
  }

  private static class SoftLightContext implements CompositeContext {
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

          if (sa == 0) continue; // Skip completely transparent pixels

          dstPixels[x] = blendChannel(sr, dr, sa, da);
          dstPixels[x + 1] = blendChannel(sg, dg, sa, da);
          dstPixels[x + 2] = blendChannel(sb, db, sa, da);
          dstPixels[x + 3] = Math.min(255, da + sa - (da * sa / 255));
        }

        dstOut.setPixels(0, y, width, 1, dstPixels);
      }
    }

    private int blendChannel(int s, int d, int sa, int da) {
      double S = s / 255.0;
      double B = d / 255.0;
      double R;

      if (S <= 0.5) {
        R = B - (1.0 - 2.0 * S) * B * (1.0 - B);
      } else {
        double D = (B <= 0.25) ? ((16.0 * B - 12.0) * B + 4.0) * B : Math.sqrt(B);
        R = B + (2.0 * S - 1.0) * (D - B);
      }

      // Scale by alpha channels for proper opacity handling
      double alphaSrc = sa / 255.0;
      double blended = B + (R - B) * alphaSrc;

      return (int) Math.clamp(blended * 255.0, 0, 255);
    }
  }
}