package micapolos.tata8.model;

public final class Flip extends Child {
  public final Bool x;
  public final Bool y;

  Flip(Bool x, Bool y) {
    super(x, y);
    this.x = x;
    this.y = y;
  }

  public static Flip flip() {
    return flip(false, false);
  }

  public static Flip flip(boolean x, boolean y) {
    return flip(Bool.bool(x), Bool.bool(y));
  }

  public static Flip flip(Bool x, Bool y) {
    return new Flip(x, y);
  }

  @Override
  public String toString() {
    return String.format("flip(x: %s, y: %s)", x, y);
  }

  static void main() {
    new Flip(Bool.bool(false), Bool.bool(true)).show();
  }
}
