package micapolos.zexy;

import static micapolos.zexy.Animation.*;
import static micapolos.zexy.Number.*;
import static micapolos.Leo.*;

public final class ParallaxRatio extends ValueComponent {
  public final Number number;

  ParallaxRatio(Animation animation, boolean isVariable, Number number) {
    super(animation, isVariable);
    this.number = number;
  }

  public static ParallaxRatio parallaxRatio(double d) {
    return parallaxRatio(number(d));
  }

  public static ParallaxRatio parallaxRatio(Number number) {
    return new ParallaxRatio(EMPTY_ANIMATION, false, number);
  }

  public ParallaxRatio with(Animation animation) {
    checkVariable();
    return new ParallaxRatio(animation, false, number);
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
