package micapolos;

import micapolos.tata8.model.*;

import static java.lang.Math.*;
import static micapolos.tata8.model.Anchor.*;
import static micapolos.tata8.model.Boolean.*;
import static micapolos.tata8.model.Clip.*;
import static micapolos.tata8.model.Event.any;
import static micapolos.tata8.model.Flip.*;
import static micapolos.tata8.model.Image.*;
import static micapolos.tata8.model.Integer.*;
import static micapolos.tata8.model.Number.*;
import static micapolos.tata8.model.Position.*;
import static micapolos.tata8.model.Sprite.*;

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

    var init = instant(stop, position.set(50, 100));

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
