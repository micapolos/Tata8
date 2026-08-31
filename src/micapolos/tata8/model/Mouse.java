package micapolos.tata8.model;

import static micapolos.tata8.Game.mouse;
import static micapolos.tata8.model.Bool.bool;
import static micapolos.tata8.model.Event.event;
import static micapolos.tata8.model.Number.number;
import static micapolos.tata8.model.Position.position;

public final class Mouse implements Showable {
  public final Position position =
    position(
      number(() -> mouse.position.x),
      number(() -> mouse.position.y));

  public final Bool isPressed = bool(mouse.button::isPressed);
  public final Event press = event(mouse.button::didPress);

  @Override
  public String toString() {
    return String.format("mouse(%s, %s)", position, isPressed);
  }

  static void main() {
    new Mouse().show();
  }
}
