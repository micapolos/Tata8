package micapolos.tata8.model;

import static micapolos.tata8.Game.mouse;
import static micapolos.tata8.model.BooleanValue.with;
import static micapolos.tata8.model.Event.with;
import static micapolos.tata8.model.DoubleValue.with;
import static micapolos.tata8.model.Position.with;

public final class Mouse implements Showable {
  public final Position position =
    Position.with(
      with(() -> mouse.position.x),
      with(() -> mouse.position.y));

  public final BooleanValue isPressed = BooleanValue.with(mouse.button::isPressed);
  public final Event press = Event.with(mouse.button::didPress);

  @Override
  public String toString() {
    return String.format("mouse(%s, %s)", position, isPressed);
  }

  static void main() {
    new Mouse().show();
  }
}
