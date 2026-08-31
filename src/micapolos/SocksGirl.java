package micapolos;

import micapolos.tata8.model.*;

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
    var sheetImage = Image.image(SocksGirl.class, "socksgirl-sheet.png");
    var sheetImages = sheetImage.sliceVertically(12);
    var directionOrNull = Direction.fromSpans(
      Key.LEFT.pressedSpan(),
      Key.RIGHT.pressedSpan(),
      Key.UP.pressedSpan(),
      Key.DOWN.pressedSpan());
    var direction = Clipped.mapValueToNonNull(directionOrNull, Direction.DOWN);
    var imageIndex = Clipped.mapValueToInteger(direction, SocksGirl::imageIndex);
    var image = Clipped.mapIntegerToValue(imageIndex, idx -> sheetImages[idx]);
    var sprite = Sprite.newSprite();
    sprite.image.init(image.value);
    image.clip.show();
  }
}
