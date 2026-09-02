package micapolos.zexy;

import static micapolos.Leo.*;

public final class Flip extends Component {
  public final Boolean x;
  public final Boolean y;

  Flip(Boolean x, Boolean y) {
    this.x = x;
    this.y = y;
  }

  @Override
  void addRunners() {
    x.addRunnersOnce();
    y.addRunnersOnce();
  }

  public static Flip newVariable() {
    return flip(Boolean.newBoolean(), Boolean.newBoolean());
  }

  public static final Flip noFlip = flip(false, false);

  public static Flip flip(boolean x, boolean y) {
    return flip(Boolean.bool(x), Boolean.bool(y));
  }

  public static Flip flip(Boolean x, Boolean y) {
    return new Flip(x, y);
  }

  @Override
  public String toString() {
    return leo("flip", leo("x", x), leo("y", y));
  }

  static void main() {
    new Flip(Boolean.bool(false), Boolean.bool(true)).show();
  }
}
