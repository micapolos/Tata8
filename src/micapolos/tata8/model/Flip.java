package micapolos.tata8.model;

public final class Flip implements Showable {
  public final Boolean x;
  public final Boolean y;

  Flip(Boolean x, Boolean y) {
    this.x = x;
    this.y = y;
  }

  public static Flip newVariable() {
    return with(Boolean.newVariable(), Boolean.newVariable());
  }

  public static final Flip none = with(false, false);

  public static Flip with(boolean x, boolean y) {
    return with(Boolean.with(x), Boolean.with(y));
  }

  public static Flip with(Boolean x, Boolean y) {
    return new Flip(x, y);
  }

  @Override
  public String toString() {
    return String.format("flip(x: %s, y: %s)", x, y);
  }

  static void main() {
    new Flip(Boolean.with(false), Boolean.with(true)).show();
  }
}
