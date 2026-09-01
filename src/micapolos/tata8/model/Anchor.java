package micapolos.tata8.model;

public final class Anchor implements Showable {
  public final Number x;
  public final Number y;

  Anchor(Number x, Number y) {
    this.x = x;
    this.y = y;
  }

  public static Anchor newVariable() {
    return with(Number.newVariable(), Number.newVariable());
  }

  public static final Anchor zero = with(Number.zero, Number.zero);

  public static Anchor with(double x, double y) {
    return with(Number.number(x), Number.number(y));
  }

  public static Anchor with(double x, Number y) {
    return with(Number.number(x), y);
  }

  public static Anchor with(Number x, double y) {
    return with(x, Number.number(y));
  }

  public static Anchor with(Number x, Number y) {
    return new Anchor(x, y);
  }

  void setImmediately(double x, double y) {
    this.x.setImmediately(x);
    this.y.setImmediately(y);
  }

  void setImmediately(Number x, Number y) {
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

  public Action set(Number x, Number y) {
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
    new Anchor(Number.number(1), Number.number(2)).show();
  }
}
