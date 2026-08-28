package micapolos.tata8;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public final class Image {
  final BufferedImage bufferedImage;
  public final FinalSize size;

  Image(BufferedImage bufferedImage) {
    this.bufferedImage = bufferedImage;
    this.size = new FinalSize(bufferedImage.getWidth(), bufferedImage.getHeight());
  }


  Image(BufferedImage bufferedImage, FinalSize size) {
    this.bufferedImage = bufferedImage;
    this.size = size;
  }

  public Canvas newCanvas() {
    return new Canvas(bufferedImage);
  }

  public Image with(BufferedImage bufferedImage) {
    return new Image(bufferedImage, new FinalSize(bufferedImage.getWidth(), bufferedImage.getHeight()));
  }

  public Image subImage(int x, int y, int width, int height) {
    return with(bufferedImage.getSubimage(x, y, width, height));
  }

  public Image[][] slice(int columnCount, int rowCount) {
    Image[] verticalImages = sliceVertically(columnCount);
    Image[][] images = new Image[columnCount][rowCount];
    for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
      images[columnIndex] = verticalImages[columnIndex].sliceHorizontally(rowCount);
    }
    return images;
  }

  public Image[] sliceVertically(int columnCount) {
    var sliceWidth = size.width / columnCount;
    Image[] images = new Image[columnCount];
    int index = 0;
    int x = 0;
    while (columnCount != 0) {
      images[index] = new Image(
          bufferedImage.getSubimage(x, 0, sliceWidth, size.height),
          new FinalSize(sliceWidth, size.height));
      x += sliceWidth;
      index++;
      columnCount--;
    }
    return images;
  }

  public Image[] sliceHorizontally(int rowCount) {
    var sliceHeight = size.height / rowCount;
    Image[] images = new Image[rowCount];
    int index = 0;
    int y = 0;
    while (rowCount != 0) {
      images[index] = new Image(
          bufferedImage.getSubimage(0, y, size.width, sliceHeight),
          new FinalSize(size.width, sliceHeight));
      y += sliceHeight;
      index++;
      rowCount--;
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

  static void main() {
    Image image = Image.load(Image.class, "quote.png");

    Image[] images = image.sliceVertically(32);
    for (int i = 0; i < images.length; i++) {
      Game.background.canvas.draw(images[i], i * 2, 0);
    }

    Image[] images2 = image.sliceHorizontally(32);
    for (int i = 0; i < images2.length; i++) {
      Game.background.canvas.draw(images2[i], 0, 32 + i * 2);
    }

    Image[][] images3 = image.slice(32, 16);
    for (int x = 0; x < 32; x++) {
      for (int y = 0; y < 16; y++) {
        Game.background.canvas.draw(images3[x][y], 64 + x*2, 64 + y*3);
      }
    }

    Game.start();
  }
}
