package micapolos.tata8.model;

import static micapolos.tata8.Game.keys;
import static micapolos.tata8.model.Span.span;

public enum Key implements Showable {
  LEFT(keys.left),
  RIGHT(keys.right),
  UP(keys.up),
  DOWN(keys.down),
  Z(keys.z),
  X(keys.x);

  private final micapolos.tata8.Key state;

  public final Event press;
  public final Event release;
  public final Boolean isPressed;

  Key(micapolos.tata8.Key state) {
    this.state = state;
    this.press = Event.with(state::pressed);
    this.release = Event.with(state::released);
    this.isPressed = Boolean.bool(state::isPressed);
  }

  public Span pressedSpan() {
    return span(isPressed, press, release);
  }

  @Override
  public String toString() {
    return String.format("Key.%s: %s", name(), state.isPressed ? "pressed" : "released");
  }

  static void main() {
    Key.LEFT.show();
  }
}
