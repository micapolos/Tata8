package micapolos.tata8;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public final class Image {
  BufferedImage bufferedImage;

  public final ReadOnlySize size = new ReadOnlySize() {
    @Override
    public int width() {
      return bufferedImage != null ? bufferedImage.getWidth() : 0;
    }

    @Override
    public int height() {
      return bufferedImage != null ? bufferedImage.getHeight() : 0;
    }
  };

  public boolean isLoaded() {
    return bufferedImage != null;
  }

  public void load(Class<?> baseClass, String fileName) {
    bufferedImage = loadBufferedImage(baseClass, fileName);
  }

  public void unload() {
    bufferedImage = null;
  }

  static BufferedImage loadBufferedImage(Class<?> baseClass, String fileName) {
    try {
      return ImageIO.read(Resource.stream(baseClass, fileName));
    } catch (IOException e) {
      throw new RuntimeException("Failed to load sprite: " + fileName);
    }
  }
}
