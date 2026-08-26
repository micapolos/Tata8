package micapolos.tata8;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public final class Image {
  final BufferedImage bufferedImage;
  public final FinalSize size;

  Image(BufferedImage bufferedImage, FinalSize size) {
    this.bufferedImage = bufferedImage;
    this.size = size;
  }

  static Image load(Class<?> baseClass, String fileName) {
    BufferedImage bufferedImage = loadBufferedImage(baseClass, fileName);
    return new Image(bufferedImage, new FinalSize(bufferedImage.getWidth(), bufferedImage.getHeight()));
  }

  static BufferedImage loadBufferedImage(Class<?> baseClass, String fileName) {
    try {
      return ImageIO.read(Resource.stream(baseClass, fileName));
    } catch (IOException e) {
      throw new RuntimeException("Failed to load sprite: " + fileName);
    }
  }
}
