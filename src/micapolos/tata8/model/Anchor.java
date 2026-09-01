package micapolos.tata8.model;

public final class Anchor implements Showable {
  public final DoubleValue x;
  public final DoubleValue y;

  Anchor(DoubleValue x, DoubleValue y) {
    this.x = x;
    this.y = y;
  }

  public static Anchor newVariable() {
    return with(DoubleValue.newVariable(), DoubleValue.newVariable());
  }

  public static final Anchor zero = with(DoubleValue.zero, DoubleValue.zero);

  public static Anchor with(double x, double y) {
    return with(DoubleValue.with(x), DoubleValue.with(y));
  }

  public static Anchor with(double x, DoubleValue y) {
    return with(DoubleValue.with(x), y);
  }

  public static Anchor with(DoubleValue x, double y) {
    return with(x, DoubleValue.with(y));
  }

  public static Anchor with(DoubleValue x, DoubleValue y) {
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
    init(with(x, y));
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
    new Anchor(DoubleValue.with(1), DoubleValue.with(2)).show();
  }
}
