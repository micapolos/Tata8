package micapolos.tata8;

import java.awt.image.BufferedImage;

public final class Font {
  public static final Image image = Game.loadImage(Font.class, "font.png");
  public static final Font mica = newFont(image.bufferedImage, 2, 1, 1);
  public static final Font kornelka = newFont(Game.loadImage(Font.class, "kor-font.png").bufferedImage, 2, 1, 1);
  public static final Font system = mica;

  final Glyph[] glyphs;
  public final int height;
  public final int spaceWidth;
  public final int glyphSpacing;
  public final int lineSpacing;

  Font(Glyph[] glyphs, int height, int spaceWidth, int glyphSpacing, int lineSpacing) {
    this.glyphs = glyphs;
    this.height = height;
    this.spaceWidth = spaceWidth;
    this.glyphSpacing = glyphSpacing;
    this.lineSpacing = lineSpacing;
  }

  public Glyph glyph(char ch) {
    int index = glyphIndex(ch);
    return index >= 0 && index < glyphs.length ? glyphs[index] : null;
  }

  public int width(String string) {
    int width = 0;
    for (int i = 0; i < string.length(); i++) {
      char ch = string.charAt(i);
      Glyph glyph = glyph(ch);
      if (i != 0) width += glyphSpacing;
      if (glyph == null) {
        width += spaceWidth;
      } else {
        width += glyph.width;
      }
    }
    return width;
  }

  public static Font newFont(BufferedImage image, int spaceWidth, int glyphSpacing, int lineSpacing) {
    Glyph[] glyphs = new Glyph[96];
    int index = 0;
    int x = 0;
    int width = image.getWidth();
    int height = image.getHeight() - 1;
    while (true) {
      if (x >= width) break;
      Glyph glyph = Glyph.read(image, x, height);
      if (glyph == null) break;
      glyphs[index] = glyph;
      x += glyph.width;
      x++;
      index++;
      if (index == glyphs.length) break;
    }
    return new Font(glyphs, height, spaceWidth, glyphSpacing, lineSpacing);
  }

  private int glyphIndex(char ch) {
    return ch - 33;
  }

  private Glyph unsafeGlyph(char ch) {
    return glyphs[glyphIndex(ch)];
  }

  void drawOn(BufferedImage image, char ch, int x, int y, int color) {
    Glyph glyph = glyph(ch);
    if (glyph != null) {
      glyph.draw(image, x, y, height, color);
    }
  }

  void drawOn(BufferedImage image, String string, int x, int y, int color) {
    int startX = x;
    for (int i = 0; i < string.length(); i++) {
      char ch = string.charAt(i);
      if (ch == 10) {
        x = startX;
        y += height + lineSpacing;
        continue;
      }
      Glyph glyph = glyph(ch);
      if (i != 0) x += glyphSpacing;
      if (glyph == null) {
        x += 2;
      } else {
        glyph.draw(image, x, y, height, color);
        x += glyph.width;
      }
    }
  }

  public void show() {
    drawOn(
      Game.background.canvas.image,
      "This is a very interesting string...\nAnd ***I LIKE IT***!!! 123456",
      10, 10,
      0xff2288dd);
    Game.start();
  }

  static void main() {
    Font.kornelka.show();
  }
}
