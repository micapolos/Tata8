package micapolos.tata8.model.clipped;

import micapolos.tata8.model.Clipped;
import micapolos.tata8.model.Integer;
import micapolos.tata8.model.Number;
import micapolos.tata8.model.Value;

import java.util.List;

public final class ClippedInteger {
  public static Clipped<Integer> negated(Clipped<Integer> a) {
    return a.update(Integer::negated);
  }

  public static Clipped<Integer> plus(Clipped<Integer> a, Clipped<Integer> b) {
    return a.update(b, Integer::plus);
  }

  public static Clipped<Integer> minus(Clipped<Integer> a, Clipped<Integer> b) {
    return a.update(b, Integer::minus);
  }

  public static Clipped<Integer> times(Clipped<Integer> a, Clipped<Integer> b) {
    return a.update(b, Integer::times);
  }

  public static Clipped<Number> toNumber(Clipped<Integer> a) {
    return a.map(Integer::toNumber);
  }

  public static <R> Clipped<Value<R>> get(Clipped<Integer> index, R... values) {
    return index.map(x -> x.getValue(values));
  }

  public static <R> Clipped<Value<R>> get(Clipped<Integer> index, List<R> values) {
    return index.map(x -> x.get(values));
  }

  private ClippedInteger() {}
}
