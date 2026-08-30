package micapolos.tata8.model;

public final class Anchor implements Showable {
  public final Number x;
  public final Number y;

  Anchor(Number x, Number y) {
    this.x = x;
    this.y = y;
  }

  static Anchor variable() {
    return new Anchor(Number.variable(), Number.variable());
  }

  public void init(double x, double y) {
    this.x.set(x);
    this.y.set(y);
  }

  public void init(Number x, Number y) {
    this.x.set(x);
    this.y.set(y);
  }

  @Override
  public String toString() {
    return String.format("anchor(x: %s, y: %s)", x, y);
  }

  static void main() {
    new Anchor(Number.with(1), Number.with(2)).show();
  }
}
