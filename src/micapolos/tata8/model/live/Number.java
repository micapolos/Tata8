package micapolos.tata8.model.live;

import micapolos.tata8.model.Live;
import micapolos.tata8.model.Showable;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import static micapolos.tata8.model.live.LiveNumbers.live;

public final class Number implements Showable {
  final Live<micapolos.tata8.model.Number> live;

  Number(Live<micapolos.tata8.model.Number> live) {
    this.live = live;
  }

  Number update(UnaryOperator<micapolos.tata8.model.Number> operator) {
    return new Number(live.update(operator));
  }

  Number update(Number y, BinaryOperator<micapolos.tata8.model.Number> operator) {
    return new Number(live.update(y.live, operator));
  }

  public static Number number(double d) {
    return new Number(live(d));
  }

  public Number negated() {
    return update(micapolos.tata8.model.Number::negated);
  }

  public Number plus(double d) {
    return plus(number(d));
  }

  public Number plus(Number n) {
    return update(n, micapolos.tata8.model.Number::plus);
  }

  public Number minus(double d) {
    return minus(number(d));
  }

  public Number minus(Number n) {
    return update(n, micapolos.tata8.model.Number::minus);
  }

  public Number times(double d) {
    return times(number(d));
  }

  public Number times(Number n) {
    return update(n, micapolos.tata8.model.Number::times);
  }

  public Number fraction() {
    return update(micapolos.tata8.model.Number::fraction);
  }

  public Integer integer() {
    return new Integer(live.map(micapolos.tata8.model.Number::integer));
  }

  @Override
  public void show() {
    live.show();
  }

  static void main() {
    number(10).plus(3.14).times(3).fraction().show();
  }
}
