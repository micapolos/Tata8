package micapolos.tata8.model;

import static micapolos.tata8.model.DoubleValue.number;

public final class Anchor implements Showable {
  public final DoubleValue x;
  public final DoubleValue y;

  Anchor(DoubleValue x, DoubleValue y) {
    this.x = x;
    this.y = y;
  }

  public static Anchor anchorVariable() {
    return anchor(DoubleValue.variable(), DoubleValue.variable());
  }

  public static Anchor anchor() {
    return anchor(DoubleValue.zero, DoubleValue.zero);
  }

  public static Anchor anchor(double x, double y) {
    return anchor(number(x), number(y));
  }

  public static Anchor anchor(double x, DoubleValue y) {
    return anchor(number(x), y);
  }

  public static Anchor anchor(DoubleValue x, double y) {
    return anchor(x, number(y));
  }

  public static Anchor anchor(DoubleValue x, DoubleValue y) {
    return new Anchor(x, y);
  }

  void setImmediately(double x, double y) {
    this.x.setImmediately(x);
    this.y.setImmediately(y);
  }

  void setImmediately(DoubleValue x, DoubleValue y) {
    this.x.setImmediately(x);
    this.y.setImmediately(y);
  }

  public void init(double x, double y) {
    init(anchor(x, y));
  }

  public void init(Anchor anchor) {
    x.init(anchor.x);
    y.init(anchor.y);
  }

  public Action set(double x, double y) {
    return () -> {
      this.x.setImmediately(x);
      this.y.setImmediately(y);
    };
  }

  public Action set(DoubleValue x, DoubleValue y) {
    return () -> {
      this.x.setImmediately(x);
      this.y.setImmediately(y);
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
