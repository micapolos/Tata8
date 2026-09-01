package micapolos.tata8.model.clipped;

import micapolos.tata8.model.Clip;
import micapolos.tata8.model.Clipped;
import micapolos.tata8.model.DoubleValue;

import static micapolos.tata8.model.Clip.clip;
import static micapolos.tata8.model.Clipped.clipped;

public final class ClippedDoubleValues {
  private ClippedDoubleValues() {}

  public static Clipped<DoubleValue> clippedNumber(double d) {
    return clipped(DoubleValue.with(d), Clip.EMPTY);
  }

  public static Clipped<DoubleValue> readonly(DoubleValue doubleValue) {
    return clipped(doubleValue.readonly(), Clip.EMPTY);
  }

  public static Clipped<DoubleValue> readonly(Clipped<DoubleValue> number) {
    return number.update(DoubleValue::readonly);
  }

  public static Clipped<DoubleValue> variable(DoubleValue initial) {
    DoubleValue variable = DoubleValue.newVariable(initial);
    return clipped(variable, clip(() -> variable.set(initial), seconds -> seconds));
  }

  public static Clipped<DoubleValue> negated(Clipped<DoubleValue> a) {
    return a.update(DoubleValue::negated);
  }

  public static Clipped<DoubleValue> plus(Clipped<DoubleValue> a, Clipped<DoubleValue> b) {
    return a.update(b, DoubleValue::plus);
  }

  public static Clipped<DoubleValue> minus(Clipped<DoubleValue> a, Clipped<DoubleValue> b) {
    return a.update(b, DoubleValue::minus);
  }

  public static Clipped<DoubleValue> times(Clipped<DoubleValue> a, Clipped<DoubleValue> b) {
    return a.update(b, DoubleValue::times);
  }

  public static Clipped<DoubleValue> fraction(Clipped<DoubleValue> a) {
    return a.update(DoubleValue::fraction);
  }

  public static Clipped<DoubleValue> clippedSeconds() {
    DoubleValue doubleValue = DoubleValue.newVariable();
    Clip clip = clip(
      () -> doubleValue.setImmediately(0),
      seconds -> {
        doubleValue.setImmediately(doubleValue.get() + seconds);
        return 0;
      });
    return clipped(doubleValue, clip);
  }
}
