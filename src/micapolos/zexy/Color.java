package micapolos.zexy;

import micapolos.tata8.Canvas;

import static micapolos.Leo.*;
import static micapolos.zexy.Number.*;

public final class Color extends Component implements Drawable {
  public final Number red;
  public final Number green;
  public final Number blue;
  public final Number alpha;

  Color(Number red, Number green, Number blue, Number alpha) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }

  @Override
  void addRunners() {
    red.addRunnersOnce();
    green.addRunnersOnce();
    blue.addRunnersOnce();
    alpha.addRunnersOnce();
  }

  public static Color rgb(double red, double green, double blue) {
    return rgba(red, green, blue, 1);
  }

  public static Color rgba(double red, double green, double blue, double alpha) {
    return rgba(Number.number(red), Number.number(green), Number.number(blue), Number.number(alpha));
  }

  public static Color rgb(Number red, Number green, Number blue) {
    return rgba(red, green, blue, Number.number(1));
  }

  public static Color rgba(Number red, Number green, Number blue, Number alpha) {
    return new Color(red, green, blue, alpha);
  }

  public static Color variable() {
    return new Color(Number.newNumber(), Number.newNumber(), Number.newNumber(), Number.newNumber());
  }

  public Action set(Color color) {
    return Action.sequence(
      red.set(color.red),
      green.set(color.green),
      blue.set(color.blue),
      alpha.set(color.alpha));
  }

  public micapolos.tata8.Color get() {
    return micapolos.tata8.Color.rgba(
      (float) red.get(),
      (float) green.get(),
      (float) blue.get(),
      (float) alpha.get());
  }

  @Override
  public String toString() {
    return leo("color",
      leo("red", red),
      leo("green", green),
      leo("blue", blue),
      leo("alpha", alpha));
  }

  @Override
  public void drawOn(Canvas canvas) {
    canvas.fillRect(128, 96, 64, 64, tata8());
  }

  micapolos.tata8.Color tata8() {
    return micapolos.tata8.Color.rgba((float) red.get(), (float) green.get(), (float) blue.get(), (float) alpha.get());
  }

  static void main() {
    var brightness = Key.Z.isPressed.select(seconds.fraction(), number(1));
    Color.rgb(
        Key.LEFT.isPressed.toNumber().times(brightness),
        Key.DOWN.isPressed.toNumber().times(brightness),
        Key.RIGHT.isPressed.toNumber().times(brightness))
      .show();
  }
}
