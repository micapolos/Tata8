package micapolos.tata8.model;

public final class Flip implements Showable {
  public final BooleanValue x;
  public final BooleanValue y;

  Flip(BooleanValue x, BooleanValue y) {
    this.x = x;
    this.y = y;
  }

  public static Flip newVariable() {
    return with(BooleanValue.newVariable(), BooleanValue.newVariable());
  }

  public static final Flip none = with(false, false);

  public static Flip with(boolean x, boolean y) {
    return with(BooleanValue.with(x), BooleanValue.with(y));
  }

  public static Flip with(BooleanValue x, BooleanValue y) {
    return new Flip(x, y);
  }

  @Override
  public String toString() {
    return String.format("flip(x: %s, y: %s)", x, y);
  }

  static void main() {
    new Flip(BooleanValue.with(false), BooleanValue.with(true)).show();
  }
}
