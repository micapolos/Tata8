package micapolos.zexy;

import static micapolos.zexy.Clip.*;
import static micapolos.zexy.Number.*;
import static micapolos.Leo.*;

public final class ParallaxRatio extends ValueComponent {
  public final Number number;

  ParallaxRatio(Clip clip, boolean isVariable, Number number) {
    super(clip, isVariable);
    this.number = number;
  }

  public static ParallaxRatio depth(double d) {
    return depth(number(d));
  }

  public static ParallaxRatio depth(Number number) {
    return new ParallaxRatio(emptyClip, false, number);
  }

  public ParallaxRatio with(Clip clip) {
    checkVariable();
    return new ParallaxRatio(clip, false, number);
  }

  @Override
  void addRunners() {
    number.addRunnersOnce();
  }

  @Override
  public String toString() {
    return leo("parallax ratio", number);
  }

  static void main() {
    depth(seconds).show();
  }
}
