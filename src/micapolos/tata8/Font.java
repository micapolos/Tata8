package micapolos.tata8;

import java.awt.image.BufferedImage;

import static micapolos.Leo.*;

public final class Font {
  public static final Font mica = newFont(Image.micaFont.bufferedImage, 2, 1, 1);
  public static final Font kora = newFont(Image.koraFont.bufferedImage, 2, 1, 1);

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
    int lineWidth = 0;
    boolean needsGlyphSpacing = false;
    for (int i = 0; i < string.length(); i++) {
      char ch = string.charAt(i);
      if (ch == 10) {
        width = java.lang.Math.max(width, lineWidth);
        lineWidth = 0;
        needsGlyphSpacing = false;
      }
      Glyph glyph = glyph(ch);
      if (needsGlyphSpacing) {
        lineWidth += glyphSpacing;
      } else {
        needsGlyphSpacing = true;
      }

      if (glyph == null) {
        lineWidth += spaceWidth;
      } else {
        lineWidth += glyph.width;
      }
    }
    return java.lang.Math.max(width, lineWidth);
  }

  public int height(String string) {
    int height = this.height;
    for (int i = 0; i < string.length(); i++) {
      char ch = string.charAt(i);
      if (ch == 10) {
        height += 1 + this.height;
      }
    }
    return height;
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
    boolean needsGlyphSpacing = false;
    for (int i = 0; i < string.length(); i++) {
      char ch = string.charAt(i);
      if (ch == 10) {
        x = startX;
        y += height + lineSpacing;
        needsGlyphSpacing = false;
        continue;
      }
      Glyph glyph = glyph(ch);
      if (needsGlyphSpacing) {
        x += glyphSpacing;
      }
      needsGlyphSpacing = true;
      if (glyph == null) {
        x += 2;
      } else {
        glyph.draw(image, x, y, height, color);
        x += glyph.width;
      }
    }
  }

  @Override
  public String toString() {
    return leo("font",
      leo("height", height),
      leo("space width", spaceWidth),
      leo("glyph spacing", glyphSpacing),
      leo("line spacing", lineSpacing));
  }

  public void show() {
    drawOn(
      Game.background.canvas.image,
      "!\"#$%&'()*+,-./0123456789:;<=>?\n" +
        "@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_\n" +
        "`abcdefghijklmnopqrstuvwxyz{|}~",
      10, 10,
      0xff2288dd);
    Game.start();
  }

  static void main() {
    kora.show();
  }
}
