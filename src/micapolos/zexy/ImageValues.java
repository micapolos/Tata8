package micapolos.zexy;

import static micapolos.zexy.Value.*;

public final class ImageValues {
  public static Integer rgbInteger(Image image, Integer x, Integer y) {
    return rgbInteger(value(image), x, y);
  }

  public static Integer rgbInteger(Value<Image> image, Integer x, Integer y) {
    return new Integer(() -> image.get().state.getRGB(x.get(), y.get())) {
      @Override
      void addRunners() {
        image.addRunnersOnce();
        x.addRunnersOnce();
        y.addRunnersOnce();
      }
    };
  }
}
