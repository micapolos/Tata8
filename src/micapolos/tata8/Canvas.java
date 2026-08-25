package micapolos.tata8;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public final class Canvas {
  final BufferedImage image;
  final Graphics2D graphics;
  final AffineTransform imageTransform = new AffineTransform();

  public Color color = Color.WHITE;

  Canvas(int width, int height) {
    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    graphics = image.createGraphics();
    graphics.setBackground(Color.TRANSPARENT.awtColor);
    graphics.clearRect(0, 0, width, height);
    graphics.setColor(Color.WHITE.awtColor);
  }

  public void clear() {
    graphics.clearRect(0, 0, image.getWidth(), image.getHeight());
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
    if (image != null && sprite.isEnabled) {
      imageTransform.setToIdentity();
      imageTransform.translate(sprite.position.x, sprite.position.y);
      imageTransform.rotate((sprite.rotation / 360f) * Math.PI * 2);
      imageTransform.scale(sprite.flip.x ? -1 : 1, sprite.flip.y ? -1 : 1);
      imageTransform.scale(1 << sprite.zoom.x, 1 << sprite.zoom.y);
      imageTransform.translate(-sprite.anchor.x, -sprite.anchor.x);
      graphics.drawImage(image.bufferedImage, imageTransform, null);
    }
  }
}
