package micapolos.tata8.model;

import static micapolos.tata8.model.Number.number;

public final class Anchor implements Showable {
  public final Number x;
  public final Number y;

  Anchor(Number x, Number y) {
    this.x = x;
    this.y = y;
  }

  public static Anchor anchorVariable() {
    return anchor(Number.variable(), Number.variable());
  }

  public static Anchor anchor() {
    return anchor(Number.zero, Number.zero);
  }

  public static Anchor anchor(double x, double y) {
    return anchor(number(x), number(y));
  }

  public static Anchor anchor(double x, Number y) {
    return anchor(number(x), y);
  }

  public static Anchor anchor(Number x, double y) {
    return anchor(x, number(y));
  }

  public static Anchor anchor(Number x, Number y) {
    return new Anchor(x, y);
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
    return String.format("anchor(x: %s, y: %s)", x, y);
  }

  static void main() {
    new Anchor(number(1), number(2)).show();
  }
}
