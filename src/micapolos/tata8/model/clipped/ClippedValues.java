package micapolos.tata8.model.clipped;

import micapolos.tata8.model.Clipped;
import micapolos.tata8.model.IntValue;
import micapolos.tata8.model.Showable;
import micapolos.tata8.model.Value;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

public final class ClippedValues {
  private ClippedValues() {}

  public static <T extends Showable, V extends Showable, R extends Showable> Clipped<Value<R>> mapValue(
    Clipped<Value<T>> x,
    Clipped<Value<V>> y,
    BiFunction<T, V, R> function
  ) {
    return x.map(y, (a, b) -> a.map(b, function));
  }

  public static <T, R> Clipped<Value<R>> mapValue(Clipped<Value<T>> clipped, Function<T, R> function) {
    return clipped.map(value -> value.map(function));
  }

  public static <T> Clipped<Value<T>> mapValueToNonNull(Clipped<Value<T>> clipped, T defaultValue) {
    return clipped.map(value -> value.mapToNotNull(defaultValue));
  }

  public static <T> Clipped<IntValue> mapValueToInteger(Clipped<Value<T>> clipped, ToIntFunction<T> function) {
    return clipped.map(value -> value.mapToInteger(function));
  }

  public static <R> Clipped<Value<R>> mapIntegerToValue(Clipped<IntValue> clipped, IntFunction<R> function) {
    return clipped.map(value -> value.mapToValue(function));
  }
}
