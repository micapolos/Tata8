package micapolos.tata8.model;

import static micapolos.tata8.model.Number.seconds;

public final class Position extends Component {
  public final Number x;
  public final Number y;

  Position(Number x, Number y) {
    super(false);
    this.x = x;
    this.y = y;
  }

  @Override
  void addClips() {
    x.maybeAddClips();
    y.maybeAddClips();
  }

  public static Position newVariable() {
    return position(Number.newVariable(), Number.newVariable());
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

  public void init(double x, double y) {
    init(Number.number(x), Number.number(y));
  }

  public void init(Number x, Number y) {
    this.x.init(x);
    this.y.init(y);
  }

  public void init(Position position) {
    init(position.x, position.y);
  }

  public Action set(double x, double y) {
    return () -> {
      this.x.setImmediately(x);
      this.y.setImmediately(y);
    };
  }

  public Action set(Number x, Number y) {
    return () -> {
      this.x.setImmediately(x);
      this.y.setImmediately(y);
    };
  }

  public Action set(Position position) {
    return Action.sequence(x.set(position.x), y.set(position.y));
  }

  public Action capture(Position position) {
    return Action.sequence(x.capture(position.x), y.capture(position.y));
  }

  @Override
  public String toString() {
    return String.format("position(x: %s, y: %s)", x, y);
  }

  static void main() {
    position(seconds, seconds.negated()).show();
  }
}
