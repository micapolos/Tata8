package micapolos.tata8.model;

import static micapolos.tata8.Game.mouse;
import static micapolos.tata8.model.Number.number;
import static micapolos.tata8.model.Position.position;

public final class Mouse implements Showable {
  public final Position position =
    position(
      number(() -> mouse.position.x),
      number(() -> mouse.position.y));

  @Override
  public String toString() {
    return String.format("mouse(%s)", position);
  }

  static void main() {
    new Mouse().show();
  }
}
