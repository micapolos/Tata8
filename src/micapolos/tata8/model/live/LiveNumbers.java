package micapolos.tata8.model.live;

import micapolos.tata8.model.Clip;
import micapolos.tata8.model.Live;
import micapolos.tata8.model.Number;

import static micapolos.tata8.model.Clip.clip;

public final class LiveNumbers {
  private LiveNumbers() {}

  public static Live<Number> live(double d) {
    return Live.live(Number.number(d), Clip.EMPTY);
  }

  public static Live<Number> newVariable(Live<Number> initial) {
    return Live.live(Number.newVariable(initial.value), Clip.EMPTY);
  }

  public static void set(Live<Number> n, Live<Number> x) {
    n.value.setImmediately(x.value);
  }

  public static Live<Number> readonly(Number number) {
    return Live.live(number.readonly(), Clip.EMPTY);
  }

  public static Live<Number> readonly(Live<Number> number) {
    return number.update(Number::readonly);
  }

  public static Live<Number> variable(Number initial) {
    Number variable = Number.newVariable(initial);
    return Live.live(variable, clip(() -> variable.set(initial), seconds -> seconds));
  }

  public static Live<Number> negated(Live<Number> a) {
    return a.update(Number::negated);
  }

  public static Live<Number> plus(Live<Number> a, Live<Number> b) {
    return a.update(b, Number::plus);
  }

  public static Live<Number> minus(Live<Number> a, Live<Number> b) {
    return a.update(b, Number::minus);
  }

  public static Live<Number> times(Live<Number> a, Live<Number> b) {
    return a.update(b, Number::times);
  }

  public static Live<Number> fraction(Live<Number> a) {
    return a.update(Number::fraction);
  }

  public static Live<Number> liveSeconds() {
    Number number = Number.newVariable();
    Clip clip = clip(
      () -> number.setImmediately(0),
      seconds -> {
        number.setImmediately(number.get() + seconds);
        return 0;
      });
    return Live.live(number, clip);
  }
}
