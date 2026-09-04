package micapolos.zexy;

import micapolos.tata8.Canvas;

import java.util.Arrays;

import static micapolos.Leo.*;
import static micapolos.zexy.Number.*;
import static micapolos.zexy.Value.value;

public final class Image implements Drawable {
  final micapolos.tata8.Image state;
  final String name;
  final Size size;

  Image(micapolos.tata8.Image state, String name) {
    this.state = state;
    this.name = name;
    this.size = new Size(number(state.size.width), number(state.size.height));
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

  public List<Value<Image>> sliceHorizontally(int columnCount) {
    micapolos.tata8.Image[] states = state.sliceHorizontally(columnCount);
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
    return Sprite.newSprite().withImage(value(this));
  }

  @Override
  public String toString() {
    return leo("image", leo("name", name), leo("width", state.size.width), leo("height", state.size.height));
  }

  @Override
  public void drawOn(Canvas canvas) {
    canvas.draw(state);
  }

  static void main() {
    image(Image.class, "depressedChicken.png").show();
  }
}
