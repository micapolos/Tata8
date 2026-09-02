package micapolos.leo;

import static micapolos.leo.Action.sequence;
import static micapolos.leo.Number.number;

public final class Anchor extends Component {
  public final Number x;
  public final Number y;

  Anchor(Number x, Number y) {
    this.x = x;
    this.y = y;
  }

  @Override
  void addClips() {
    x.maybeAddClips();
    y.maybeAddClips();
  }

  public static Anchor newVariable() {
    return anchor(Number.newNumber(), Number.newNumber());
  }

  public static final Anchor topLeftAnchor = anchor(Number.zero, Number.zero);

  public static Anchor anchor(double x, double y) {
    return anchor(number(x), number(y));
  }

  public static Anchor anchor(double x, Number y) {
    return anchor(number(x), y);
  }

  public static Anchor anchor(Number x, double y) {
    return anchor(x, number(y));
  }

  public static Anchor anchor(Number x, Number y) {
    return new Anchor(x, y);
  }

  public Action set(double x, double y) {
    return set(number(x), number(y));
  }

  public Action set(Number x, Number y) {
    return sequence(this.x.set(x), this.y.set(y));
  }

  @Override
  public String toString() {
    return String.format("anchor(x: %s, y: %s)", x, y);
  }

  static void main() {
    new Anchor(number(1), number(2)).show();
  }
}
