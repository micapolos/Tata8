package micapolos.zexy;

import java.util.Arrays;

import static micapolos.Leo.*;
import static micapolos.zexy.Value.value;

public final class Image implements Showable {
  final micapolos.tata8.Image state;
  final String name;
  final Size size;

  Image(micapolos.tata8.Image state, String name) {
    this.state = state;
    this.name = name;
    this.size = new Size(Number.number(state.size.width), Number.number(state.size.height));
  }

  public static Image image(Class<?> baseClass, String fileName) {
    return new Image(micapolos.tata8.Game.loadImage(baseClass, fileName), fileName);
  }

  public Image crop(int x, int y, int width, int height) {
    return new Image(state.crop(x, y, width, height), name);
  }

  public List<Value<Image>> sliceVertically(int columnCount) {
    micapolos.tata8.Image[] states = state.sliceVertically(columnCount);
    Image[] images = new Image[states.length];
    for (int i = 0; i < states.length; i++) {
      images[i] = new Image(states[i], name + ":" + i);
    }
    return new List<>(Arrays.stream(images).map(Value::value).toList()) {
      @Override
      public Value<Image> get(Integer index) {
        return index.mapToValue(i -> images[i]);
      }
    };
  }

  public Sprite sprite() {
    return Sprite.newSprite().with(value(this));
  }

  @Override
  public String toString() {
    return leo("image", leo("name", name), leo("width", state.size.width), leo("height", state.size.height));
  }

  @Override
  public void show() {
    micapolos.tata8.Game.background.canvas.draw(
      state,
      (int) Game.size.width.minus(size.width).times(0.5).get(),
      (int) Game.size.height.minus(size.height).times(0.5).get());
    micapolos.tata8.Game.start();
  }

  static void main() {
    image(Image.class, "depressedChicken.png").show();
  }
}
