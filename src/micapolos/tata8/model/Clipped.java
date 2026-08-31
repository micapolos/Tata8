package micapolos.tata8.model;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

// TODO: Consider renaming to Spanned<T>.
public final class Clipped<T> implements Showable {
  public final T value;
  public final Clip clip;

  Clipped(T value, Clip clip) {
    this.value = value;
    this.clip = clip;
  }

  public static <T> Clipped<T> clipped(T value, Clip clip) {
    return new Clipped<>(value, clip);
  }

  public static <T> Clipped<List<T>> parallel(Clipped<T>... clippeds) {
    return clipped(
      Arrays.stream(clippeds).map(t -> t.value).toList(),
      Clip.parallel(Arrays.stream(clippeds).map(t -> t.clip).toList().toArray(new Clip[0])));
  }

  public <R> Clipped<R> map(Function<T, R> function) {
    return clipped(function.apply(value), clip);
  }

  public static <T, R> Clipped<Value<R>> mapValue(Clipped<Value<T>> clipped, Function<T, R> function) {
    return clipped.map(value -> value.map(function));
  }

  public static <T> Clipped<Value<T>> mapValueToNonNull(Clipped<Value<T>> clipped, T defaultValue) {
    return clipped.map(value -> value.mapToNotNull(defaultValue));
  }

  public static <T> Clipped<Integer> mapValueToInteger(Clipped<Value<T>> clipped, ToIntFunction<T> function) {
    return clipped.map(value -> value.mapToInteger(function));
  }

  public static <R> Clipped<Value<R>> mapIntegerToValue(Clipped<Integer> clipped, IntFunction<R> function) {
    return clipped.map(value -> value.map(function));
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
