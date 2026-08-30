package micapolos.tata8.model;

public final class Flip implements Showable {
  public final Boolean x;
  public final Boolean y;

  Flip(Boolean x, Boolean y) {
    this.x = x;
    this.y = y;
  }

  static Flip with(boolean x, boolean y) {
    return new Flip(Boolean.with(x), Boolean.with(y));
  }

  static Flip with(Boolean x, Boolean y) {
    return new Flip(x, y);
  }

  static Flip variable() {
    return new Flip(Boolean.variable(), Boolean.variable());
  }

  @Override
  public String toString() {
    return String.format("flip(x: %s, y: %s)", x, y);
  }

  static void main() {
    new Flip(Boolean.with(false), Boolean.with(true)).show();
  }
}
