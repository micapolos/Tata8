package micapolos.zexy;

import static micapolos.zexy.Animation.*;
import static micapolos.zexy.Number.*;
import static micapolos.Leo.*;

public final class ParallaxRatio extends ValueComponent {
  public final Number number;

  ParallaxRatio(Animation animation, Number number) {
    this.animation = animation;
    this.number = number;
  }

  public static final ParallaxRatio noParallaxRatio = parallaxRatio(1);

  public static ParallaxRatio parallaxRatio(double d) {
    return parallaxRatio(number(d));
  }

  public static ParallaxRatio parallaxRatio(Number number) {
    return new ParallaxRatio(noAnimation, number);
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
    parallaxRatio(numberOfSeconds).show();
  }

  static double applyParallaxRatio(double obj, double anchor, double camera, double parallaxRatio) {
    return anchor + (obj - camera) * parallaxRatio;
  }
}
