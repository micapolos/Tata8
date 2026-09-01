package micapolos;

import micapolos.tata8.model.Direction;
import micapolos.tata8.model.Image;
import micapolos.tata8.model.Key;
import micapolos.tata8.model.live.LiveIntegers;
import micapolos.tata8.model.live.LiveValues;

import static micapolos.tata8.model.Sprite.newSprite;

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
    var sheetImage = Image.load(SocksGirl.class, "socksgirl-sheet.png");
    var sheetImages = sheetImage.sliceVertically(12);
    var liveDirectionOrNullValue = Direction.fromSpans(
      Key.LEFT.pressedSpan(),
      Key.RIGHT.pressedSpan(),
      Key.UP.pressedSpan(),
      Key.DOWN.pressedSpan());
    var liveDirectionValue = LiveValues.mapValueToNonNull(liveDirectionOrNullValue, Direction.DOWN);
    var liveImageIndex = LiveValues.mapValueToInteger(liveDirectionValue, SocksGirl::imageIndex);
    var liveImageValue = LiveIntegers.get(liveImageIndex, sheetImages);
    var liveSprite = liveImageValue.map(image -> {
      var sprite = newSprite();
      sprite.image.init(image);
      sprite.position.init(100, 100);
      sprite.anchor.init(32, 64);
      return sprite;
    });
    liveSprite.show();
  }
}
