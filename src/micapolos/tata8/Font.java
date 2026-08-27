package micapolos.tata8;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class Font {
  public static final Font system = load();

  public final int height;
  final BufferedImage bufferedImage;

  Font(BufferedImage bufferedImage) {
    this.bufferedImage = bufferedImage;
    this.height = bufferedImage.getHeight();
  }

  static Font load() {
    Image image = Image.load(Font.class, "font.png");
    return new Font(image.bufferedImage);
  }

  void setGlyph(IntVector position, Size size, int glyph) {
    int imageX = 0;
    while (glyph > 0) {
      int partCount = glyph == 1 ? 2 : 1;
      for (int part = 0; part < partCount; part++) {
        while (imageX < bufferedImage.getWidth() && columnHasPixel(imageX)) {
          imageX++;
        }
        while (imageX < bufferedImage.getWidth() && !columnHasPixel(imageX)) {
          imageX++;
        }
      }
      glyph--;
    }

    int startX = imageX;
    while (imageX < bufferedImage.getWidth() && columnHasPixel(imageX)) {
      imageX++;
    }
    int endX = imageX;
    int width = endX - startX;
    int height = bufferedImage.getHeight();
    position.set(startX, 0);
    size.set(width, height);
  }

  public int getWidth(char ch) {
    IntVector position = new IntVector();
    Size size = new Size();
    setGlyph(position, size, ch - 32);
    return size.width == 0 ? 2 : size.width;
  }

  public int getWidth(String string) {
    if (string.isEmpty()) return 0;
    int width = -1;
    for (char ch : string.toCharArray()) {
      width++;
      width += getWidth(ch);
    }
    return width;
  }

  int draw(Graphics2D graphics, int glyph, int x, int y) {
    IntVector position = new IntVector();
    Size size = new Size();
    setGlyph(position, size, glyph);
    int width = size.width;
    int height = size.height;
    int startX = position.x;
    if (size.width != 0) {
      graphics.drawImage(bufferedImage, x, y, x + width, y + height, startX, 0, startX + width, height, null);
    }
    return width;
  }

  void draw(Graphics2D graphics, String string, int x, int y) {
    for (int ch : string.toCharArray()) {
      int width = ch <= 32 ? 0 : draw(graphics, ch - 33, x, y);
      x += (width == 0 ? 2 : width) + 1;
    }
  }

  boolean hasPixel(int x, int y) {
    return bufferedImage.getRGB(x, y) != 0;
  }

  boolean columnHasPixel(int x) {
    for (int y = 0; y < bufferedImage.getHeight(); y++) {
      if (hasPixel(x, y)) {
        return true;
      }
    }
    return false;
  }

  static void main() {
    Game.backgroundCanvas.draw("This is a very interesting string, and ***I LIKE IT***!!!", 10, 10);
    Game.start();
  }
}
