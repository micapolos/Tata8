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

  public Image[] slice(int width, int height) {
    var sliceSize = new FinalSize(width, height);
    int columnCount = size.width / width;
    int rowCount = size.height / height;
    Image[] images = new Image[columnCount * rowCount];
    int y = 0;
    int index = 0;
    for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
      int x = 0;
      for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
        images[index++] = new Image(bufferedImage.getSubimage(x, y, width, height), sliceSize);
        x += width;
      }
      y += height;
    }
    return images;
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
