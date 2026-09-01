package micapolos;

import micapolos.tata8.model.Direction;
import micapolos.tata8.model.Image;
import micapolos.tata8.model.Key;

import static micapolos.tata8.model.Anchor.anchor;
import static micapolos.tata8.model.Position.position;
import static micapolos.tata8.model.Sprite.sprite;

public class SocksGirl {
  static int imageIndex(Direction direction) {
    return switch (direction) {
      case UP -> 8;
      case UP_RIGHT -> 7;
      case RIGHT -> 6;
      case DOWN_RIGHT -> 5;
      case DOWN -> 0;
      case DOWN_LEFT -> 11;
      case LEFT -> 10;
      case UP_LEFT -> 9;
    };
  }

  static void main() {
    var sheetImages = Image.image(SocksGirl.class, "socksgirl-sheet.png").sliceVertically(12);

    var image = Direction
      .fromSpans(Key.LEFT.pressedSpan(), Key.RIGHT.pressedSpan(), Key.UP.pressedSpan(), Key.DOWN.pressedSpan())
      .orIfNull(Direction.DOWN)
      .mapToInteger(SocksGirl::imageIndex)
      .selectFrom(sheetImages);

    sprite()
      .with(image)
      .with(anchor(32, 64))
      .with(position(100, 100))
      .show();
  }
}
