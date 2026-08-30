package micapolos.tata8.model;

import static micapolos.tata8.Game.mouse;

public final class Mouse implements Showable {
  public final Position position =
    Position.with(
      Number.with(() -> mouse.position.x),
      Number.with(() -> mouse.position.y));

  @Override
  public String toString() {
    return String.format("mouse(%s)", position);
  }

  static void main() {
    new Mouse().show();
  }
}
