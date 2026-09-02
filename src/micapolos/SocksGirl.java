package micapolos;

import micapolos.zexy.Direction;
import micapolos.zexy.Key;

import static micapolos.zexy.Anchor.*;
import static micapolos.zexy.Image.*;
import static micapolos.zexy.Sprite.*;

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
    var sheetImages = image(SocksGirl.class, "socksgirl-sheet.png").sliceVertically(12);

    var image = Direction
      .fromSpans(Key.LEFT.pressedSpan(), Key.RIGHT.pressedSpan(), Key.UP.pressedSpan(), Key.DOWN.pressedSpan())
      .orIfNull(Direction.DOWN)
      .mapToInteger(SocksGirl::imageIndex)
      .selectFrom(sheetImages);

    newSprite()
      .with(image)
      .with(anchor(32, 64))
      .show();
  }
}
