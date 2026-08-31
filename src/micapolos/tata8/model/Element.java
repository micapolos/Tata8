package micapolos.tata8.model;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public final class Element<T> implements Showable {
  public final T value;
  public final Clip clip;

  Element(T value, Clip clip) {
    this.value = value;
    this.clip = clip;
  }

  public static <T> Element<T> element(T value, Clip clip) {
    return new Element<>(value, clip);
  }

  public static <T> Element<List<T>> parallel(Element<T>... elements) {
    return element(
      Arrays.stream(elements).map(t -> t.value).toList(),
      Clip.parallel(Arrays.stream(elements).map(t -> t.clip).toList().toArray(new Clip[0])));
  }

  public <R> Element<R> map(Function<T, R> function) {
    return element(function.apply(value), clip);
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
