package micapolos.tata8.model;

import static micapolos.tata8.model.Game.seconds;

public final class Position implements Showable {
  final boolean isVariable;
  public final Number x;
  public final Number y;

  Position(boolean isVariable, Number x, Number y) {
    this.isVariable = isVariable;
    this.x = x;
    this.y = y;
  }

  static Position zero = with(0, 0);

  static Position with(double x, double y) {
    return with(Number.with(x), Number.with(y));
  }

  static Position with(Number x, Number y) {
    return new Position(false, x, y);
  }

  static Position variable() {
    return variable(0, 0);
  }

  static Position variable(double x, double y) {
    return new Position(true, Number.variable(x), Number.variable(y));
  }

  public void init(double x, double y) {
    if (!isVariable) {
      throw new IllegalArgumentException("Not a variable.");
    }
    this.x.init(x);
    this.y.init(y);
  }

  public void init(Number x, Number y) {
    if (!isVariable) {
      throw new IllegalArgumentException("Not a variable.");
    }
    this.x.init(x);
    this.y.init(y);
  }

  @Override
  public String toString() {
    return String.format("position(x: %s, y: %s)", x, y);
  }

  static void main() {
    Position.with(seconds, seconds).show();
  }
}
