package micapolos;

import micapolos.zexy.Key;

import static java.lang.Math.*;
import static micapolos.zexy.Anchor.*;
import static micapolos.zexy.Animation.*;
import static micapolos.zexy.Boolean.*;
import static micapolos.zexy.Camera.*;
import static micapolos.zexy.Event.any;
import static micapolos.zexy.Flip.*;
import static micapolos.zexy.Image.*;
import static micapolos.zexy.Integer.*;
import static micapolos.zexy.Number.*;
import static micapolos.zexy.On.*;
import static micapolos.zexy.ParallaxRatio.*;
import static micapolos.zexy.Position.*;
import static micapolos.zexy.Scale.*;
import static micapolos.zexy.Sprite.*;
import static micapolos.zexy.Stack.*;

final class Chicken {
  static void main() {
    var images = image(Chicken.class, "depressedChicken.png").sliceVertically(8);

    var xx = newNumber();
    var imageIndex = newInteger();
    var isLeft = newBoolean();
    var position = newPosition();
    var sprite = newSprite()
      .withImage(images.get(imageIndex))
      .with(anchor(16, 28))
      .with(position(xx, number(0)))
      .with(flip(isLeft, bool(false)));

    var speed = Key.Z.isPressed.ifTrue(2.0).orElse(1.0);
    var step = isLeft.ifTrue(speed.negated().times(3)).orElse(speed.times(3));
    var move = xx.add(step);

    var startWalking = sequence(8, i -> frame(imageIndex.set(floorMod(i + 3, 8)).then(move)))
      .stretch(0.1f)
      .repeat();

    var stop = imageIndex.set(2);

    var init = instant(stop, position.set(0, 0), camera.anchor.set(160, 128));

    var animation = init.then(
      parallel(
        select(
          on(Key.RIGHT.press).lets(isLeft.set(false).then(startWalking)),
          on(Key.LEFT.press).lets(isLeft.set(true).then(startWalking)),
          on(any(Key.RIGHT.release, Key.LEFT.release)).lets(stop)),
        Key.Z.isPressed.startLoggingWith("Z")));

    sprite = sprite.with(animation);

    var x = xx.elastic(0.25).loggedWith("x");
    var sprites = stackOf(
      sprite
        .with(position(x, position.y.minus(48)))
        .with(scale(0.25, 0.25))
        .with(parallaxRatio(0.25)),
      sprite
        .with(position(x, position.y.minus(32)))
        .with(scale(0.5, 0.5))
        .with(parallaxRatio(0.5)),
      sprite
        .with(position(x, position.y.minus(0)))
        .with(animation),
      sprite
        .with(position(x, position.y.plus(64)))
        .with(scale(2, 2))
        .with(parallaxRatio(2)));

    sprites.show();
  }
}
