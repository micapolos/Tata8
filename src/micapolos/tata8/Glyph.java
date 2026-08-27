package micapolos.tata8;

import java.awt.image.BufferedImage;

public final class Glyph {
  final int[] vLines;
  public final int width;

  Glyph(int[] vLines, int width) {
    this.vLines = vLines;
    this.width = width;
  }

  static Glyph read(BufferedImage image, int x, int parts) {
    int[] vLines = new int[8];
    int width = readVLinesWidth(vLines, image, x, parts, image.getHeight());
    return width == 0 ? null : new Glyph(vLines, width);
  }

  static int readVLinesWidth(int[] vLines, BufferedImage image, int x, int parts, int height) {
    int width = 0;
    while (true) {
      if (x == image.getWidth()) break;
      int line = readVLine(image, x, height);
      if (line == 0) {
        parts--;
        if (parts == 0) break;
      }
      vLines[width] = line;
      x++;
      width++;
      if (width == vLines.length) break;
    }
    return width;
  }

  static int readVLine(BufferedImage image, int x, int height) {
    int vLine = 0;
    int y = height;
    while (height != 0) {
      y--;
      vLine <<= 1;
      height--;
      int color = image.getRGB(x, y);
      vLine |= color == 0 ? 0 : 1;
    }
    return vLine;
  }

  void draw(BufferedImage image, int x, int y, int height, int color) {
    drawVLines(image, x, y, vLines, height, color);
  }

  static void drawVLine(BufferedImage image, int x, int y, int line, int height, int color) {
    while (height != 0) {
      if (y >= 0 && y < image.getHeight()) {
        if ((line & 0x1) != 0) {
          image.setRGB(x, y, color);
        }
      }
      line >>= 1;
      height--;
      y++;
    }
  }

  static void drawVLines(BufferedImage image, int x, int y, int[] lines, int height, int color) {
    for (int line : lines) {
      if (x >= 0 && x < image.getWidth()) {
        drawVLine(image, x, y, line, height, color);
      }
      x++;
    }
  }

  static void main() {
    Image image = Image.load(Glyph.class, "font.png");
    Glyph glyph = Glyph.read(image.bufferedImage, 6, 1);
    glyph.draw(Game.foregroundCanvas.image, 10, 10, 8, 0xff559911);
    Game.start();
  }
}
