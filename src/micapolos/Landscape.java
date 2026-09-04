package micapolos;

import micapolos.tata8.Composite;
import micapolos.zexy.Key;

import static micapolos.zexy.Anchor.*;
import static micapolos.zexy.Animation.*;
import static micapolos.zexy.Camera.*;
import static micapolos.zexy.Condition.*;
import static micapolos.zexy.Image.*;
import static micapolos.zexy.Number.*;
import static micapolos.zexy.ParallaxRatio.*;
import static micapolos.zexy.Position.*;
import static micapolos.zexy.Sprite.*;
import static micapolos.zexy.Stack.*;

public class Landscape {
  static void main() {

    var girlX = newNumber();
    var cameraX = girlX.toElastic(0.05);

    var startMoving = select(
      when(Key.RIGHT.isPressed).keep(girlX.adding(300)),
      when(Key.LEFT.isPressed).keep(girlX.adding(-300)));

    var girlImage = image(Landscape.class, "socksgirl-sheet.png").sliceVertically(12).get(6);

    var girlSprite = newSprite()
      .withImage(girlImage)
      .with(anchor(16, 64))
      .with(position(girlX, 50))
      .with(parallaxRatio(3.0 / 6.0));

    var image = image(Landscape.class, "landscape.png");
    var images = image.sliceHorizontally(7);
    var backSprites = stack(4, i ->
      newSprite()
        .withImage(images.get(i))
        .with(anchor(1024, 128))
        .with(parallaxRatio((double) i / 6.0))
        .with(startMoving));

    var frontSprites = stack(3, i ->
      newSprite()
        .withImage(images.get(i + 4))
        .with(anchor(1024, 128))
        .with(parallaxRatio((double) (i + 4) / 6.0))
        .with(i + 4 == 4 ? Composite.SOFT_LIGHT : i + 4 == 5 ? Composite.MULTIPLY : Composite.NORMAL)
        .with(startMoving));

    var startGame = parallel(
      instant(camera.anchor.set(160, 128)),
      instant(camera.position.x.set(cameraX)),
      startMoving);

    var stack = stackOf(backSprites, girlSprite, frontSprites);

    stack.lets(startGame);
    stack.show();
  }
}
