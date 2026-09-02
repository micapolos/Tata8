package micapolos;

import micapolos.zexy.Game;
import micapolos.zexy.Key;

import static java.lang.Math.*;
import static micapolos.zexy.Anchor.*;
import static micapolos.zexy.Boolean.*;
import static micapolos.zexy.Clip.*;
import static micapolos.zexy.Event.any;
import static micapolos.zexy.Flip.*;
import static micapolos.zexy.Image.*;
import static micapolos.zexy.Integer.*;
import static micapolos.zexy.Number.*;
import static micapolos.zexy.Position.*;
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

    var init = instant(stop, position.set(160, 160));

    var clip = init.then(
      parallel(
        select(on(Game.mouse.press, instant(position.capture(Game.mouse.position)))),
        select(
          on(Key.RIGHT.press, instant(isLeft.set(false)).then(walk)),
          on(Key.LEFT.press, instant(isLeft.set(true)).then(walk)),
          on(any(Key.RIGHT.release, Key.LEFT.release), instant(stop)))));

    sprite.with(clip).show();
  }
}
