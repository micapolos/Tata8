package micapolos.tata8.model;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

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

  @Override
  public String toString() {
    return value.toString();
  }

  @Override
  public void show() {
    clip.showWith(this);
  }
}
