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

  public static ParallaxRatio parallaxRatio(double d) {
    return parallaxRatio(number(d));
  }

  public static ParallaxRatio parallaxRatio(Number number) {
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
    parallaxRatio(seconds).show();
  }
}
