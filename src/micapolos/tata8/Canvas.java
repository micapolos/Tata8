package micapolos.tata8;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public final class Canvas {
  final BufferedImage image;
  final Graphics2D graphics;
  final AffineTransform imageTransform = new AffineTransform();

  public Color color = Color.WHITE;
  public Font font = Font.system;
  public boolean textHasShadow;

  Canvas(BufferedImage image) {
    this.image = image;
    graphics = image.createGraphics();
    graphics.setBackground(Color.TRANSPARENT.awtColor);
    graphics.clearRect(0, 0, image.getWidth(), image.getHeight());
    graphics.setColor(Color.WHITE.awtColor);
  }

  Canvas(int width, int height) {
    this(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
  }

  public void set(Composite composite) {
    graphics.setComposite(composite.awt);
  }

  public void clear() {
    graphics.clearRect(0, 0, image.getWidth(), image.getHeight());
  }

  public void drawPoint(int x, int y) {
    drawPoint(x, y, color);
  }

  public void drawPoint(int x, int y, Color color) {
    fillRect(x, y, 1, 1, color);
  }

  public void drawRect(int x, int y, int w, int h) {
    drawRect(x, y, w, h, color);
  }

  public void drawRect(int x, int y, int w, int h, Color color) {
    graphics.setColor(color.awtColor);
    graphics.drawRect(x, y, w, h);
  }

  public void fillRect(int x, int y, int w, int h) {
    fillRect(x, y, w, h, color);
  }

  public void fillRect(int x, int y, int w, int h, Color color) {
    graphics.setColor(color.awtColor);
    graphics.fillRect(x, y, w, h);
  }

  public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
    fillTriangle(x1, y1, x2, y2, x3, y3, color);
  }

  public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color) {
    // TODO: Pre-allocate these.
    int[] xs = {x1, x2, x3};
    int[] ys = {y1, y2, y3};
    graphics.fillPolygon(xs, ys, 3);
  }

  public void draw(Image image) {
    draw(image, 0, 0);
  }

  public void draw(Image image, int x, int y) {
    draw(image, x, y, false, false);
  }

  public void draw(Image image, int x, int y, boolean flipX, boolean flipY) {
    BufferedImage awtImage = image.bufferedImage;
    if (awtImage != null) {
      imageTransform.setToIdentity();
      imageTransform.translate(x, y);
      if (flipX) {
        imageTransform.translate(image.bufferedImage.getWidth(), 0);
        imageTransform.scale(-1, 1);
      }
      if (flipY) {
        imageTransform.translate(0, image.bufferedImage.getHeight());
        imageTransform.scale(1, -1);
      }
      graphics.drawImage(awtImage, imageTransform, null);
    }
  }

  public void draw(Sprite sprite) {
    draw(sprite, 0, 0);
  }

  public void draw(Sprite sprite, float x, float y) {
    Image image = sprite.image;
    if (image != null && !sprite.isHidden) {
      draw(image,
        sprite.anchor.x, sprite.anchor.y,
        Math.round(x) + Math.round(sprite.position.x), Math.round(y) + Math.round(sprite.position.y),
        sprite.flip.x, sprite.flip.y,
        sprite.scale.x, sprite.scale.y,
        Composite.NORMAL,
        sprite.angle);
    }
  }

  public void draw(
    Image image,
    float anchorX, float anchorY,
    float positionX, float positionY,
    boolean flipX, boolean flipY,
    float scaleX, float scaleY,
    Composite composite,
    float angle) {
    imageTransform.setToIdentity();
    imageTransform.translate(Math.round(positionX), Math.round(positionY));
    imageTransform.rotate((angle / 360f) * Math.TAU);
    imageTransform.scale(flipX ? -1 : 1, flipY ? -1 : 1);
    imageTransform.scale(scaleX, scaleY);
    imageTransform.translate(-anchorX, -anchorY);
    graphics.setComposite(composite.awt);
    graphics.drawImage(image.bufferedImage, imageTransform, null);
  }

  public void draw(String text, int x, int y) {
    draw(text, x, y, color);
  }

  public void draw(String text, int x, int y, Color color) {
    draw(text, x, y, color, font);
  }

  public void draw(String text, int x, int y, Color color, Font font) {
    draw(text, x, y, color, font, textHasShadow);
  }

  public void draw(String text, int x, int y, Color color, Font font, boolean shadow) {
    int rgb = color.awtColor.getRGB();
    int blackRgb = Color.BLACK.awtColor.getRGB();
    if (font != null) {
      if (shadow) {
        font.drawOn(image, text, x, y - 1, blackRgb);
        font.drawOn(image, text, x - 1, y, blackRgb);
        font.drawOn(image, text, x + 1, y, blackRgb);
        font.drawOn(image, text, x, y + 1, blackRgb);
      }
      font.drawOn(image, text, x, y, rgb);
    }
  }

  @Override
  public String toString() {
    return "canvas";
  }
}
