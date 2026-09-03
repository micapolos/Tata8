package micapolos.zexy;

import static micapolos.zexy.Action.sequence;
import static micapolos.Leo.*;
import static micapolos.zexy.Number.*;

public final class Scale extends Component {
  public final Number x;
  public final Number y;

  Scale(Number x, Number y) {
    this.x = x;
    this.y = y;
  }

  @Override
  void addRunners() {
    x.addRunnersOnce();
    y.addRunnersOnce();
  }

  public static Scale newVariable() {
    return scale(Number.newNumber(), Number.newNumber());
  }

  public static final Scale noScale = scale(numberOne, numberOne);

  public static Scale scale(double x, double y) {
    return scale(number(x), number(y));
  }

  public static Scale scale(double x, Number y) {
    return scale(number(x), y);
  }

  public static Scale scale(Number x, double y) {
    return scale(x, number(y));
  }

  public static Scale scale(Number x, Number y) {
    return new Scale(x, y);
  }

  public Action set(double x, double y) {
    return set(number(x), number(y));
  }

  public Action set(Number x, Number y) {
    return sequence(this.x.set(x), this.y.set(y));
  }

  @Override
  public String toString() {
    return leo("scale", leo("x", x), leo("y", y));
  }

  static void main() {
    new Scale(number(1), number(2)).show();
  }
}
