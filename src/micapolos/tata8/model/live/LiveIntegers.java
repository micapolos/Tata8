package micapolos.tata8.model.live;

import micapolos.tata8.model.Live;
import micapolos.tata8.model.Integer;
import micapolos.tata8.model.Number;
import micapolos.tata8.model.Value;

import java.util.List;

public final class LiveIntegers {
  private LiveIntegers() {}

  public static Live<Integer> negated(Live<Integer> a) {
    return a.update(Integer::negated);
  }

  public static Live<Integer> plus(Live<Integer> a, Live<Integer> b) {
    return a.update(b, Integer::plus);
  }

  public static Live<Integer> minus(Live<Integer> a, Live<Integer> b) {
    return a.update(b, Integer::minus);
  }

  public static Live<Integer> times(Live<Integer> a, Live<Integer> b) {
    return a.update(b, Integer::times);
  }

  public static Live<Number> toNumber(Live<Integer> a) {
    return a.map(Integer::toNumber);
  }

  public static <R> Live<Value<R>> get(Live<Integer> index, R... values) {
    return index.map(x -> x.getValue(values));
  }

  public static <R> Live<Value<R>> get(Live<Integer> index, List<R> values) {
    return index.map(x -> x.get(values));
  }
}
