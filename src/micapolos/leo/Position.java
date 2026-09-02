package micapolos.leo;

import static micapolos.leo.Action.*;
import static micapolos.leo.Number.*;
import static micapolos.leo.Strings.*;

public final class Position extends Component {
  public final Number x;
  public final Number y;

  Position(Number x, Number y) {
    this.x = x;
    this.y = y;
  }

  @Override
  void addRunners() {
    x.addRunnersOnce();
    y.addRunnersOnce();
  }

  public static Position newPosition() {
    return position(Number.newNumber(), Number.newNumber());
  }

  public static Position position(double x, double y) {
    return position(Number.number(x), Number.number(y));
  }

  public static Position position(double x, Number y) {
    return position(Number.number(x), y);
  }

  public static Position position(Number x, double y) {
    return position(x, Number.number(y));
  }

  public static Position position(Number x, Number y) {
    return new Position(x, y);
  }

  public Action set(double x, double y) {
    return set(number(x), number(y));
  }

  public Action set(Number x, Number y) {
    return sequence(this.x.set(x), this.y.set(y));
  }

  public Action set(Position position) {
    return set(position.x, position.y);
  }

  public Action capture(Position position) {
    return sequence(x.capture(position.x), y.capture(position.y));
  }

  @Override
  public String toString() {
    return leo("position", leo("x", x), leo("y", y));
  }

  static void main() {
    position(seconds, seconds.negated()).show();
  }
}
