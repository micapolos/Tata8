package micapolos.tata8.model.live;

import micapolos.tata8.model.Live;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public final class Integer {
  final Live<micapolos.tata8.model.Integer> live;

  Integer(Live<micapolos.tata8.model.Integer> live) {
    this.live = live;
  }

  Integer update(UnaryOperator<micapolos.tata8.model.Integer> operator) {
    return new Integer(live.update(operator));
  }

  Integer update(Integer y, BinaryOperator<micapolos.tata8.model.Integer> operator) {
    return new Integer(live.update(y.live, operator));
  }

  public Integer negated() {
    return update(micapolos.tata8.model.Integer::negated);
  }

  public Integer plus(Integer n) {
    return update(n, micapolos.tata8.model.Integer::plus);
  }

  public Integer minus(Integer n) {
    return update(n, micapolos.tata8.model.Integer::minus);
  }

  public Integer times(Integer n) {
    return update(n, micapolos.tata8.model.Integer::times);
  }

  public Number number() {
    return new Number(live.map(micapolos.tata8.model.Integer::number));
  }
}
