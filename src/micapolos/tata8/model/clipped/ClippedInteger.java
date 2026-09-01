package micapolos.tata8.model.clipped;

import micapolos.tata8.model.Clipped;
import micapolos.tata8.model.IntValue;
import micapolos.tata8.model.DoubleValue;
import micapolos.tata8.model.Value;

import java.util.List;

public final class ClippedInteger {
  public static Clipped<IntValue> negated(Clipped<IntValue> a) {
    return a.update(IntValue::negated);
  }

  public static Clipped<IntValue> plus(Clipped<IntValue> a, Clipped<IntValue> b) {
    return a.update(b, IntValue::plus);
  }

  public static Clipped<IntValue> minus(Clipped<IntValue> a, Clipped<IntValue> b) {
    return a.update(b, IntValue::minus);
  }

  public static Clipped<IntValue> times(Clipped<IntValue> a, Clipped<IntValue> b) {
    return a.update(b, IntValue::times);
  }

  public static Clipped<DoubleValue> toNumber(Clipped<IntValue> a) {
    return a.map(IntValue::toNumber);
  }

  public static <R> Clipped<Value<R>> get(Clipped<IntValue> index, R... values) {
    return index.map(x -> x.getValue(values));
  }

  public static <R> Clipped<Value<R>> get(Clipped<IntValue> index, List<R> values) {
    return index.map(x -> x.get(values));
  }

  private ClippedInteger() {}
}
