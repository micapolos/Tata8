package micapolos.tata8.model;

import static micapolos.tata8.Game.mouse;
import static micapolos.tata8.model.Boolean.with;
import static micapolos.tata8.model.Event.with;
import static micapolos.tata8.model.Number.with;
import static micapolos.tata8.model.Position.with;

public final class Mouse implements Showable {
  public final Position position =
    Position.with(
      with(() -> mouse.position.x),
      with(() -> mouse.position.y));

  public final Boolean isPressed = Boolean.with(mouse.button::isPressed);
  public final Event press = Event.with(mouse.button::didPress);

  @Override
  public String toString() {
    return String.format("mouse(%s, %s)", position, isPressed);
  }

  static void main() {
    new Mouse().show();
  }
}
