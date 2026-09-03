package micapolos.zexy;

import micapolos.Leo;
import micapolos.tata8.Canvas;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import static micapolos.zexy.Integer.*;

public class List<T extends Component> extends Component implements Drawable {
  final java.util.List<T> components;

  List(java.util.List<T> components) {
    super(Animation.noAnimation);
    this.components = components;
  }

  public static <T extends Component> List<T> listOf(T... components) {
    return new List<>(Arrays.stream(components).toList());
  }

  public static <T extends Component> List<T> list(int size, IntFunction<T> function) {
    return new List<>(IntStream.range(0, size).mapToObj(function).toList());
  }

  @Override
  final void addRunners() {
    for (T component : components) {
      component.addRunnersOnce();
    }
  }

  public final T get(int index) {
    return get(integer(index));
  }

  @Override
  public void drawOn(Canvas canvas) {
    for (T component : components) {
      if (component instanceof Drawable drawable) {
        drawable.drawOn(canvas);
      }
    }
  }

  @Override
  public String toString() {
    return Leo.leo("list", components.toArray());
  }

  public T get(Integer index) {
    return components.get(index.get());
  }
}
