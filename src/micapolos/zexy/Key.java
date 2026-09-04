package micapolos.zexy;

import static micapolos.tata8.Game.*;
import static micapolos.zexy.Boolean.*;
import static micapolos.zexy.Event.*;
import static micapolos.zexy.Span.*;

public enum Key implements Showable {
  LEFT(keys.left),
  RIGHT(keys.right),
  UP(keys.up),
  DOWN(keys.down),
  Z(keys.z),
  X(keys.x);

  public final Event press;
  public final Event release;
  public final Boolean isPressed;

  Key(micapolos.tata8.Key state) {
    this.press = event(state::pressed);
    this.release = event(state::released);
    this.isPressed = bool(state::isPressed);
  }

  Key(Boolean isPressed) {
    this.isPressed = isPressed;
    this.press = isPressed.changeTo(true);
    this.release = isPressed.changeTo(false);
  }

  public Span pressedSpan() {
    return span(isPressed, press, release);
  }

  @Override
  public String toString() {
    return String.format("Key.%s: %s", name(), isPressed.get() ? "pressed" : "released");
  }

  static void main() {
    Key.LEFT.show();
  }
}
