package micapolos.tata8.model;

import static micapolos.tata8.Game.keys;
import static micapolos.tata8.model.Condition.onlyIf;
import static micapolos.tata8.model.Event.when;

public enum Key implements Showable {
  LEFT(keys.left),
  RIGHT(keys.right),
  UP(keys.up),
  DOWN(keys.down),
  Z(keys.z),
  X(keys.x);

  private final micapolos.tata8.Key state;

  public final Event pressed;
  public final Event released;
  public final Condition isPressed;
  public final Condition isReleased;

  Key(micapolos.tata8.Key state) {
    this.state = state;
    this.pressed = when(state::pressed);
    this.released = when(state::pressed);
    this.isPressed = onlyIf(state::pressed);
    this.isReleased = onlyIf(state::pressed);
  }

  @Override
  public String toString() {
    return String.format("Key.%s: %s", name(), state.isPressed ? "pressed" : "released");
  }

  static void main() {
    Key.LEFT.show();
  }
}
