package micapolos.tata8.model;

import static micapolos.tata8.model.Number.number;

public final class Position implements Showable {
  public final Number x;
  public final Number y;

  Position(Number x, Number y) {
    this.x = x;
    this.y = y;
  }

  public static Position variable() {
    return position(Number.variable(), Number.variable());
  }

  public static Position position(double x, double y) {
    return position(number(x), number(y));
  }

  public static Position position(double x, Number y) {
    return position(number(x), y);
  }

  public static Position position(Number x, double y) {
    return position(x, number(y));
  }

  public static Position position(Number x, Number y) {
    return new Position(x, y);
  }

  public void init(double x, double y) {
    init(number(x), number(y));
  }

  public void init(Number x, Number y) {
    this.x.init(x);
    this.y.init(y);
  }

  public void init(Position position) {
    init(position.x, position.y);
  }

  public Action set(double x, double y) {
    return () -> {
      this.x.setImmediately(x);
      this.y.setImmediately(y);
    };
  }

  public Action set(Number x, Number y) {
    return () -> {
      this.x.setImmediately(x);
      this.y.setImmediately(y);
    };
  }

  public Action set(Position position) {
    return Action.sequence(x.set(position.x), y.set(position.y));
  }

  public Action capture(Position position) {
    return Action.sequence(x.capture(position.x), y.capture(position.y));
  }

  @Override
  public String toString() {
    return String.format("position(x: %s, y: %s)", x, y);
  }

  static void main() {
    position(Game.seconds, Game.seconds.negated()).show();
  }
}
