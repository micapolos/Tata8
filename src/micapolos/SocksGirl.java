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
    var clippedDirectionOrNullValue = Direction.fromSpans(
      Key.LEFT.pressedSpan(),
      Key.RIGHT.pressedSpan(),
      Key.UP.pressedSpan(),
      Key.DOWN.pressedSpan());
    var clippedDirectionValue = Clipped.mapValueToNonNull(clippedDirectionOrNullValue, Direction.DOWN);
    var clippedImageInteger = Clipped.mapValueToInteger(clippedDirectionValue, SocksGirl::imageIndex);
    var clippedImageValue = Clipped.mapIntegerToValue(clippedImageInteger, idx -> sheetImages[idx]);
    var clippedSprite = clippedImageValue.map(image -> {
      var sprite = Sprite.newSprite();
      sprite.image.init(image);
      sprite.position.init(100, 100);
      return sprite;
    });
    clippedSprite.show();
  }
}
