package micapolos.tata8.model.live;

import micapolos.tata8.model.Live;
import micapolos.tata8.model.Integer;
import micapolos.tata8.model.Showable;
import micapolos.tata8.model.Value;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

public final class LiveValues {
  private LiveValues() {}

  public static <T extends Showable, V extends Showable, R extends Showable> Live<Value<R>> mapValue(
    Live<Value<T>> x,
    Live<Value<V>> y,
    BiFunction<T, V, R> function
  ) {
    return x.map(y, (a, b) -> a.map(b, function));
  }

  public static <T, R> Live<Value<R>> mapValue(Live<Value<T>> live, Function<T, R> function) {
    return live.map(value -> value.map(function));
  }

  public static <T> Live<Value<T>> mapValueToNonNull(Live<Value<T>> live, T defaultValue) {
    return live.map(value -> value.orIfNull(defaultValue));
  }

  public static <T> Live<Integer> mapValueToInteger(Live<Value<T>> live, ToIntFunction<T> function) {
    return live.map(value -> value.mapToInteger(function));
  }

  public static <R> Live<Value<R>> mapIntegerToValue(Live<Integer> live, IntFunction<R> function) {
    return live.map(value -> value.mapToValue(function));
  }
}
