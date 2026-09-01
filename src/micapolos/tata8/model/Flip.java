package micapolos.tata8.model;

public final class Flip extends Component {
  public final Boolean x;
  public final Boolean y;

  Flip(Boolean x, Boolean y) {
    this.x = x;
    this.y = y;
  }

  @Override
  void addClips() {
    x.maybeAddClips();
    y.maybeAddClips();
  }

  public static Flip newVariable() {
    return with(Boolean.newVariable(), Boolean.newVariable());
  }

  public static final Flip noFlip = with(false, false);

  public static Flip with(boolean x, boolean y) {
    return with(Boolean.bool(x), Boolean.bool(y));
  }

  public static Flip with(Boolean x, Boolean y) {
    return new Flip(x, y);
  }

  @Override
  public String toString() {
    return String.format("flip(x: %s, y: %s)", x, y);
  }

  static void main() {
    new Flip(Boolean.bool(false), Boolean.bool(true)).show();
  }
}
