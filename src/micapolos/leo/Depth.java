package micapolos.leo;

import static micapolos.leo.Clip.*;
import static micapolos.leo.Number.*;
import static micapolos.leo.Strings.*;

public final class Depth extends ValueComponent {
  public final Number number;

  Depth(Clip clip, boolean isVariable, Number number) {
    super(clip, isVariable);
    this.number = number;
  }

  public static Depth depth(double d) {
    return depth(number(d));
  }

  public static Depth depth(Number number) {
    return new Depth(emptyClip, false, number);
  }

  public Depth with(Clip clip) {
    checkVariable();
    return new Depth(clip, false, number);
  }

  @Override
  void addRunners() {
    number.addRunnersOnce();
  }

  @Override
  public String toString() {
    return leo("depth", number);
  }

  static void main() {
    depth(seconds).show();
  }
}
