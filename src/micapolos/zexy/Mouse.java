package micapolos.zexy;

import static micapolos.tata8.Game.*;
import static micapolos.zexy.Number.*;
import static micapolos.zexy.Position.*;

public final class Mouse implements Showable {
  public final Position position =
    position(
      number(() -> mouse.position.x),
      number(() -> mouse.position.y));

  public final Boolean isPressed = Boolean.bool(mouse.button::isPressed);
  public final Event press = Event.event(mouse.button::didPress);

  @Override
  public String toString() {
    return String.format("mouse(%s, %s)", position, isPressed);
  }

  static void main() {
    new Mouse().show();
  }
}
