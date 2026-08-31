package micapolos.tata8.model;

import static micapolos.tata8.model.Number.number;
import static micapolos.tata8.model.Number.numberVariable;

public final class Position implements Showable {
  public final Number x;
  public final Number y;

  Position(Number x, Number y) {
    this.x = x;
    this.y = y;
  }

  public static Position positionVariable() {
    return position(numberVariable(), numberVariable());
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

  void init(double x, double y) {
    this.x.init(x);
    this.y.init(y);
  }

  void init(Number x, Number y) {
    this.x.init(x);
    this.y.init(y);
  }

  public Action set(double x, double y) {
    return () -> {
      this.x.init(x);
      this.y.init(y);
    };
  }

  public Action set(Number x, Number y) {
    return () -> {
      this.x.init(x);
      this.y.init(y);
    };
  }

  @Override
  public String toString() {
    return String.format("position(x: %s, y: %s)", x, y);
  }

  static void main() {
    position(Number.seconds, Number.seconds).show();
  }
}
