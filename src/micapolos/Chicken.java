package micapolos;

import micapolos.zexy.Key;

import static java.lang.Math.*;
import static micapolos.zexy.Anchor.*;
import static micapolos.zexy.Animation.*;
import static micapolos.zexy.Boolean.*;
import static micapolos.zexy.Event.any;
import static micapolos.zexy.Flip.*;
import static micapolos.zexy.Image.*;
import static micapolos.zexy.Integer.*;
import static micapolos.zexy.List.*;
import static micapolos.zexy.Number.*;
import static micapolos.zexy.On.*;
import static micapolos.zexy.ParallaxRatio.*;
import static micapolos.zexy.Position.*;
import static micapolos.zexy.Scale.*;
import static micapolos.zexy.Sprite.*;

final class Chicken {
  static void main() {
    var images = image(Chicken.class, "depressedChicken.png").sliceVertically(8);

    var imageIndex = newInteger(0);
    var isLeft = newBoolean();
    var position = newPosition();
    var sprite = newSprite()
      .with(images.get(imageIndex))
      .with(anchor(16, 28))
      .with(position)
      .with(flip(isLeft, bool(false)));

    var speed = Key.Z.isPressed.select(number(2), number(1));
    var step = isLeft.select(speed.negated().times(3), speed.times(3));
    var move = position.x.add(step);

    var walk = sequence(8, i -> frame(imageIndex.set(floorMod(i + 3, 8)).then(move)))
      .stretch(0.1f)
      .repeat();

    var stop = imageIndex.set(2);

    var init = instant(stop, position.set(0, 0));

    var animation = init.then(
      parallel(
        select(
          on(Key.RIGHT.press).start(isLeft.set(false).then(walk)),
          on(Key.LEFT.press).start(isLeft.set(true).then(walk)),
          on(any(Key.RIGHT.release, Key.LEFT.release)).execute(stop))));

    sprite = sprite.with(animation);

    var sprites = listOf(
      sprite
        .with(position(position.x, position.y.minus(48)))
        .with(scale(0.25, 0.25))
        .with(parallaxRatio(0.25)),
      sprite
        .with(position(position.x, position.y.minus(32)))
        .with(scale(0.5, 0.5))
        .with(parallaxRatio(0.5)),
      sprite.with(animation),
      sprite
        .with(position(position.x, position.y.plus(64)))
        .with(scale(2, 2))
        .with(parallaxRatio(2)));

    sprites.show();
  }
}
