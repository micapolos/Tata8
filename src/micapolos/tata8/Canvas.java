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
    Image image = sprite.image;
    if (image != null && !sprite.isHidden) {
      imageTransform.setToIdentity();
      imageTransform.translate(sprite.position.x, sprite.position.y);
      imageTransform.rotate((sprite.angle / 360f) * Math.TAU);
      imageTransform.scale(sprite.flip.x ? -1 : 1, sprite.flip.y ? -1 : 1);
      imageTransform.scale(sprite.scale.x, sprite.scale.y);
      imageTransform.translate(-sprite.anchor.x, -sprite.anchor.y);
      graphics.drawImage(image.bufferedImage, imageTransform, null);
    }
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
        font.draw(image, text, x, y - 1, blackRgb);
        font.draw(image, text, x - 1, y, blackRgb);
        font.draw(image, text, x + 1, y, blackRgb);
        font.draw(image, text, x, y + 1, blackRgb);
      }
      font.draw(image, text, x, y, rgb);
    }
  }
}
