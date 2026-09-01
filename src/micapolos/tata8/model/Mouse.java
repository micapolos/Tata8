package micapolos.tata8.model;

import static micapolos.tata8.Game.mouse;
import static micapolos.tata8.model.BooleanValue.bool;
import static micapolos.tata8.model.Event.event;
import static micapolos.tata8.model.DoubleValue.number;
import static micapolos.tata8.model.Position.position;

public final class Mouse implements Showable {
  public final Position position =
    position(
      number(() -> mouse.position.x),
      number(() -> mouse.position.y));

  public final BooleanValue isPressed = bool(mouse.button::isPressed);
  public final Event press = event(mouse.button::didPress);

  @Override
  public String toString() {
    return String.format("mouse(%s, %s)", position, isPressed);
  }

  static void main() {
    new Mouse().show();
  }
}
