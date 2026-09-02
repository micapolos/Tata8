package micapolos.leo;

import static micapolos.tata8.Game.mouse;
import static micapolos.leo.Number.number;

public final class Mouse implements Showable {
  public final Position position =
    Position.position(
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
