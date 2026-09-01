package micapolos.tata8.model;

import micapolos.tata8.model.clipped.ClippedNumber;

import java.util.Arrays;
import java.util.List;
import java.util.function.*;
import java.util.stream.Stream;

public abstract class Clipped<T extends Showable> implements Showable {
  public final T value;

  Clipped(T value) {
    this.value = value;
  }

  abstract Stream<Clip> clips();

  final void addClips() {
    clips().distinct().forEach(Game::add);
  }

  public static <T extends Showable> Clipped<T> clipped(T value, Clip clip) {
    return new Clipped<>(value) {
      @Override
      Stream<Clip> clips() {
        return Stream.of(clip);
      }
    };
  }

  public Clipped<T> update(UnaryOperator<T> operator) {
    return new Clipped<T>(operator.apply(value)) {
      @Override
      Stream<Clip> clips() {
        return Clipped.this.clips();
      }
    };
  }

  public Clipped<T> update(Clipped<T> x, BinaryOperator<T> operator) {
    return new Clipped<T>(operator.apply(value, x.value)) {
      @Override
      Stream<Clip> clips() {
        return Stream.concat(Clipped.this.clips(), x.clips());
      }
    };
  }

  public <R extends Showable> Clipped<R> map(Function<T, R> function) {
    return new Clipped<>(function.apply(value)) {
      @Override
      Stream<Clip> clips() {
        return Clipped.this.clips();
      }
    };
  }

  public <V extends Showable, R extends Showable> Clipped<R> map(Clipped<V> x, BiFunction<T, V, R> function) {
    return new Clipped<>(function.apply(value, x.value)) {
      @Override
      Stream<Clip> clips() {
        return Stream.concat(Clipped.this.clips(), x.clips());
      }
    };
  }

  public static <T extends Showable, V extends Showable, R extends Showable> Clipped<Value<R>> mapValue(Clipped<Value<T>> x, Clipped<Value<V>> y, BiFunction<T, V, R> function) {
    return x.map(y, (a, b) -> a.map(b, function));
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
    return clipped.map(value -> value.mapToValue(function));
  }

  @Override
  public String toString() {
    return value.toString();
  }

  @Override
  public void show() {
    addClips();
    value.show();
  }

  static void main() {
    var s1 = Number.clippedSeconds();
    var s2 = Number.clippedSeconds();
    var s12 = ClippedNumber.plus(s1, s2);
    s12.clips().distinct().forEach(c -> c.step(10));
    IO.println("Should be 10: " + s1.value.get());
    IO.println("Should be 10: " + s2.value.get());
    IO.println("Should be 20: " + s12.value.get());
  }
}
