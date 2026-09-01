package micapolos.tata8.model;

import static micapolos.tata8.model.DoubleValue.with;

public final class Color implements Showable {
  public final DoubleValue red;
  public final DoubleValue green;
  public final DoubleValue blue;
  public final DoubleValue alpha;

  Color(DoubleValue red, DoubleValue green, DoubleValue blue, DoubleValue alpha) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }

  public static Color rgb(double red, double green, double blue) {
    return rgba(red, green, blue, 1);
  }

  public static Color rgba(double red, double green, double blue, double alpha) {
    return rgba(DoubleValue.with(red), DoubleValue.with(green), DoubleValue.with(blue), DoubleValue.with(alpha));
  }

  public static Color rgb(DoubleValue red, DoubleValue green, DoubleValue blue) {
    return rgba(red, green, blue, DoubleValue.with(1));
  }

  public static Color rgba(DoubleValue red, DoubleValue green, DoubleValue blue, DoubleValue alpha) {
    return new Color(red, green, blue, alpha);
  }

  public static Color variable() {
    return new Color(DoubleValue.newVariable(), DoubleValue.newVariable(), DoubleValue.newVariable(), DoubleValue.newVariable());
  }

  public Action set(Color color) {
    return Action.sequence(red.set(color.red), green.set(color.green), blue.set(color.blue), alpha.set(color.alpha));
  }

  public micapolos.tata8.Color get() {
    return micapolos.tata8.Color.rgba((float) red.get(), (float) green.get(), (float) blue.get(), (float) alpha.get());
  }

  @Override
  public void show() {
    Game.when(Game.started, Background.color.set(this));
    Game.show();
  }

  static void main() {
    Color.rgb(
      Key.LEFT.isPressed.select(DoubleValue.one, DoubleValue.zero),
      Key.DOWN.isPressed.select(DoubleValue.one, DoubleValue.zero),
      Key.RIGHT.isPressed.select(DoubleValue.one, DoubleValue.zero)).show();
  }
}
