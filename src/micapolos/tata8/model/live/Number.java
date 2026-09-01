package micapolos.tata8.model.live;

import micapolos.tata8.model.Live;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public final class Number {
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

  public Number negated() {
    return update(micapolos.tata8.model.Number::negated);
  }

  public Number plus(Number n) {
    return update(n, micapolos.tata8.model.Number::plus);
  }

  public Number minus(Number n) {
    return update(n, micapolos.tata8.model.Number::minus);
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
}
