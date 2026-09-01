package micapolos.tata8.model;

import static micapolos.tata8.model.Number.number;
import static micapolos.tata8.model.Number.seconds;

public final class Color extends Component {
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
  void addClips() {
    red.maybeAddClips();
    green.maybeAddClips();
    blue.maybeAddClips();
    alpha.maybeAddClips();
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
    return Action.sequence(red.set(color.red), green.set(color.green), blue.set(color.blue), alpha.set(color.alpha));
  }

  public micapolos.tata8.Color get() {
    return micapolos.tata8.Color.rgba((float) red.get(), (float) green.get(), (float) blue.get(), (float) alpha.get());
  }

  @Override
  public void show() {
    Game.on(Game.start, Background.color.set(this));
    Game.show();
  }

  static void main() {
    Color.rgb(seconds, number(0), number(0)).show();
//    Color.rgb(
//      Key.LEFT.isPressed.select(Number.one, Number.zero),
//      Key.DOWN.isPressed.select(Number.one, Number.zero),
//      Key.RIGHT.isPressed.select(Number.one, Number.zero)).show();
  }
}
