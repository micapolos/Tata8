package micapolos.tata8;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class Image {
  BufferedImage bufferedImage;

  public boolean isLoaded;
  public final Size size = new Size();

  public void load(String fileName) {
    bufferedImage = loadBufferedImage(fileName);
    size.set(bufferedImage.getWidth(), bufferedImage.getHeight());
    isLoaded = true;
  }

  static BufferedImage loadBufferedImage(String fileName) {
    try {
      return ImageIO.read(new File(fileName));
    } catch (IOException e) {
      throw new RuntimeException("Failed to load sprite: " + fileName);
    }
  }
}
