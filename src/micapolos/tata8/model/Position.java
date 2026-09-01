package micapolos.tata8.model;

import static micapolos.tata8.model.Number.number;
import static micapolos.tata8.model.Seconds.seconds;

public final class Position implements Showable {
  public final Number x;
  public final Number y;

  Position(Number x, Number y) {
    this.x = x;
    this.y = y;
  }

  public static Position newVariable() {
    return with(Number.newVariable(), Number.newVariable());
  }

  public static Position with(double x, double y) {
    return with(Number.number(x), Number.number(y));
  }

  public static Position with(double x, Number y) {
    return with(Number.number(x), y);
  }

  public static Position with(Number x, double y) {
    return with(x, Number.number(y));
  }

  public static Position with(Number x, Number y) {
    return new Position(x, y);
  }

  public void init(double x, double y) {
    init(Number.number(x), Number.number(y));
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
    with(seconds, seconds.negated()).show();
  }
}
