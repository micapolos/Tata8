package micapolos.awt;

import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;

public class DuskFilter {

  public static void applyDuskFilter(BufferedImage image, double factor) {
    // Clamp factor between 0.0 and 1.0
    double t = factor;
    if (t < 0.0) {
      t = 0.0;
    }
    if (t > 1.0) {
      t = 1.0;
    }

    // Interpolate target color multipliers based on factor t
    // Factor 0.0 -> Multipliers (1.0, 1.0, 1.0)
    // Factor 1.0 -> Multipliers (0.25, 0.35, 0.60)
    double redScale = 1.0 + (0.25 - 1.0) * t;
    double greenScale = 1.0 + (0.35 - 1.0) * t;
    double blueScale = 1.0 + (0.60 - 1.0) * t;

    byte[] redTable = new byte[256];
    byte[] greenTable = new byte[256];
    byte[] blueTable = new byte[256];
    byte[] alphaTable = new byte[256];

    int i = 0;

    while (i < 256) {
      double r = (double) i * redScale;
      double g = (double) i * greenScale;
      double b = (double) i * blueScale;

      if (r > 255.0) {
        r = 255.0;
      }
      if (g > 255.0) {
        g = 255.0;
      }
      if (b > 255.0) {
        b = 255.0;
      }

      redTable[i] = (byte) ((int) r);
      greenTable[i] = (byte) ((int) g);
      blueTable[i] = (byte) ((int) b);
      alphaTable[i] = (byte) i;

      i = i + 1;
    }

    byte[][] lookupData;
    if (image.getColorModel().hasAlpha()) {
      lookupData = new byte[][]{redTable, greenTable, blueTable, alphaTable};
    } else {
      lookupData = new byte[][]{redTable, greenTable, blueTable};
    }

    ByteLookupTable lookupTable = new ByteLookupTable(0, lookupData);
    LookupOp colorOp = new LookupOp(lookupTable, null);

    // Supplying 'image' as both source and destination executes the filter in-place
    colorOp.filter(image, image);
  }
}