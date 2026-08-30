package micapolos.tata8.model;

public final class Position implements Showable {
  public final Number x;
  public final Number y;

  Position(Number x, Number y) {
    this.x = x;
    this.y = y;
  }

  static Position zero = with(0, 0);

  static Position with(double x, double y) {
    return with(Number.with(x), Number.with(y));
  }

  static Position with(Number x, Number y) {
    return new Position(x, y);
  }

  static Position variable() {
    return with(Number.variable(), Number.variable());
  }

  static Position variable(double x, double y) {
    return with(Number.variable(), Number.variable());
  }

  @Override
  public String toString() {
    return String.format("position(x: %s, y: %s)", x, y);
  }

  static void main() {
    new Position(Number.with(1), Number.with(2)).show();
  }
}
