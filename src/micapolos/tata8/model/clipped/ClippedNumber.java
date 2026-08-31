package micapolos.tata8.model.clipped;

import micapolos.tata8.model.Clipped;
import micapolos.tata8.model.Number;

public final class ClippedNumber {
  public static Clipped<Number> negated(Clipped<Number> a) {
    return a.update(Number::negated);
  }

  public static Clipped<Number> plus(Clipped<Number> a, Clipped<Number> b) {
    return a.update(b, Number::plus);
  }

  public static Clipped<Number> minus(Clipped<Number> a, Clipped<Number> b) {
    return a.update(b, Number::minus);
  }

  public static Clipped<Number> times(Clipped<Number> a, Clipped<Number> b) {
    return a.update(b, Number::times);
  }

  public static Clipped<Number> fraction(Clipped<Number> a) {
    return a.update(Number::fraction);
  }

  private ClippedNumber() {}
}
