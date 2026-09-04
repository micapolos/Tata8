package micapolos.zexy;

import static micapolos.Leo.*;
import static micapolos.zexy.Action.*;
import static micapolos.zexy.Number.*;
import static micapolos.zexy.Number.number;

public class Position extends Component {
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
    return position(newNumber(), newNumber());
  }

  public static final Position positionZero = position(0, 0);

  public static Position position(double x, double y) {
    return position(number(x), number(y));
  }

  public static Position position(double x, Number y) {
    return position(number(x), y);
  }

  public static Position position(Number x, double y) {
    return position(x, number(y));
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

  public Position logged() {
    return new Position(x, y) {
      @Override
      void addRunners() {
        Game.add(new Runner() {
          @Override
          public void update(float seconds) {
            micapolos.tata8.Game.log(Position.this);
          }
        });
      }
    };
  }

  public Position loggedAs(String label) {
    return new Position(x, y) {
      @Override
      void addRunners() {
        Game.add(new Runner() {
          @Override
          public void update(float seconds) {
            micapolos.tata8.Game.log(leo(label, Position.this));
          }
        });
      }
    };
  }

  @Override
  public String toString() {
    return leo("position", leo("x", x), leo("y", y));
  }

  static void main() {
    position(numberOfSeconds, numberOfSeconds.negated()).show();
  }
}
