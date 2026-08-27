package micapolos.tata8;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class Font {
  static int SPACE_WIDTH = 2;
  static int GLYPH_SPACING = 1;

  public static final Font system = load(Image.loadBufferedImage(Font.class, "font.png"));
  public final Glyph[] glyphs;

  public final int height;

  Font(Glyph[] glyphs, int height) {
    this.glyphs = glyphs;
    this.height = height;
  }

  static Font load(BufferedImage image) {
    Glyph[] glyphs = new Glyph[96];
    int index = 0;
    int x = 0;
    int width = image.getWidth();
    while (true) {
      if (x >= width) break;
      int parts = index == 1 ? 2 : 1;  // special case for "
      Glyph glyph = Glyph.read(image, x, parts);
      if (glyph == null) break;
      glyphs[index] = glyph;
      x += glyph.width;
      x++;
      index++;
      if (index == glyphs.length) break;
    }
    return new Font(glyphs, image.getHeight());
  }

  private int glyphIndex(char ch) {
    return ch - 33;
  }

  private Glyph glyph(char ch) {
    return glyphs[glyphIndex(ch)];
  }

  private Glyph glyphOrNull(char ch) {
    int index = glyphIndex(ch);
    return index >= 0 && index < glyphs.length ? glyphs[index] : null;
  }

  void draw(BufferedImage image, char ch, int x, int y, int color) {
    Glyph glyph = glyphOrNull(ch);
    if (glyph != null) {
      glyph.draw(image, x, y, height, color);
    }
  }

  void draw(BufferedImage image, String string, int x, int y, int color) {
    for (int i = 0; i < string.length(); i++) {
      char ch = string.charAt(i);
      Glyph glyph = glyphOrNull(ch);
      if (i != 0) x += GLYPH_SPACING;
      if (glyph == null) {
        x += 2;
      } else {
        glyph.draw(image, x, y, height, color);
        x += glyph.width;
      }
    }
  }

  public int width(String string) {
    int width = 0;
    for (int i = 0; i < string.length(); i++) {
      char ch = string.charAt(i);
      Glyph glyph = glyphOrNull(ch);
      if (i != 0) width += GLYPH_SPACING;
      if (glyph == null) {
        width += SPACE_WIDTH;
      } else {
        width += glyph.width;
      }
    }
    return width;
  }

  static void main() {
    Font.system.draw(Game.backgroundCanvas.image, "This is a very interesting string, and ***I LIKE IT***!!!", 10, 10, 0xff2288dd);
    Game.start();
  }
}
