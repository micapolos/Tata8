package micapolos.tata8.model;

public final class Flip implements Showable {
  public final BooleanValue x;
  public final BooleanValue y;

  Flip(BooleanValue x, BooleanValue y) {
    this.x = x;
    this.y = y;
  }

  public static Flip flipVariable() {
    return flip(BooleanValue.variable(), BooleanValue.variable());
  }

  public static Flip flip() {
    return flip(false, false);
  }

  public static Flip flip(boolean x, boolean y) {
    return flip(BooleanValue.bool(x), BooleanValue.bool(y));
  }

  public static Flip flip(BooleanValue x, BooleanValue y) {
    return new Flip(x, y);
  }

  @Override
  public String toString() {
    return String.format("flip(x: %s, y: %s)", x, y);
  }

  static void main() {
    new Flip(BooleanValue.bool(false), BooleanValue.bool(true)).show();
  }
}
