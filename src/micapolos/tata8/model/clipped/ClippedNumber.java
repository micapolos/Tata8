package micapolos.tata8.model.clipped;

import micapolos.tata8.model.Clip;
import micapolos.tata8.model.Clipped;
import micapolos.tata8.model.Number;

import static micapolos.tata8.model.Clip.clip;
import static micapolos.tata8.model.Clipped.clipped;

public final class ClippedNumber {
  public static Clipped<Number> clippedNumber(double d) {
    return clipped(Number.number(d), Clip.EMPTY);
  }

  public static Clipped<Number> readonly(Number number) {
    return clipped(number.readonly(), Clip.EMPTY);
  }

  public static Clipped<Number> readonly(Clipped<Number> number) {
    return number.update(Number::readonly);
  }

  public static Clipped<Number> variable(Number initial) {
    Number variable = Number.variable(initial);
    return clipped(variable, clip(() -> variable.set(initial), seconds -> seconds));
  }

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
