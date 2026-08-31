package micapolos.tata8.model;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public final class Animated<T> implements Showable {
  public final T value;
  public final Clip clip;

  Animated(T value, Clip clip) {
    this.value = value;
    this.clip = clip;
  }

  public static <T> Animated<T> animated(T value, Clip clip) {
    return new Animated<>(value, clip);
  }

  public static <T> Animated<List<T>> parallel(Animated<T>... animateds) {
    return animated(
      Arrays.stream(animateds).map(t -> t.value).toList(),
      Clip.parallel(Arrays.stream(animateds).map(t -> t.clip).toList().toArray(new Clip[0])));
  }

  public <R> Animated<R> map(Function<T, R> function) {
    return animated(function.apply(value), clip);
  }

  @Override
  public String toString() {
    return value.toString();
  }

  @Override
  public void show() {
    clip.showWith(this);
  }
}
