package micapolos.tata8.model.live;

import micapolos.tata8.model.Live;
import micapolos.tata8.model.Showable;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public final class Integer implements Showable {
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

  public static Integer integer(int i) {
    return new Integer(LiveIntegers.live(i));
  }

  public Integer negated() {
    return update(micapolos.tata8.model.Integer::negated);
  }

  public Integer plus(int i) {
    return plus(integer(i));
  }

  public Integer plus(Integer i) {
    return update(i, micapolos.tata8.model.Integer::plus);
  }

  public Integer minus(int i) {
    return minus(integer(i));
  }

  public Integer minus(Integer i) {
    return update(i, micapolos.tata8.model.Integer::minus);
  }

  public Integer times(int i) {
    return times(integer(i));
  }

  public Integer times(Integer n) {
    return update(n, micapolos.tata8.model.Integer::times);
  }

  public Number number() {
    return new Number(live.map(micapolos.tata8.model.Integer::number));
  }

  @Override
  public void show() {
    live.show();
  }

  static void main() {
    integer(10).plus(123).times(2).number().show();
  }
}
