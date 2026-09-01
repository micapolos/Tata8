package micapolos;

import micapolos.tata8.model.*;
import micapolos.tata8.model.Boolean;
import micapolos.tata8.model.Integer;
import micapolos.tata8.model.Number;

import static micapolos.tata8.model.Anchor.anchor;
import static micapolos.tata8.model.Boolean.bool;
import static micapolos.tata8.model.Clip.*;
import static micapolos.tata8.model.Event.any;
import static micapolos.tata8.model.Flip.flip;
import static micapolos.tata8.model.Sprite.newSprite;

final class Chicken {
  static void main() {
    var images = Image.image(Chicken.class, "depressedChicken.png").sliceVertically(8);

    var imageIndex = Integer.newVariable(0);
    var isLeft = Boolean.newVariable();
    var position = Position.newVariable();
    var sprite = newSprite()
      .with(images.get(imageIndex))
      .with(anchor(16, 0))
      .with(position)
      .with(flip(isLeft, bool(false)));

    var speed = Key.Z.isPressed.select(Number.number(2), Number.number(1));
    var step = isLeft.select(speed.negated().times(3), speed.times(3));
    var move = position.x.add(step);

    var walk = Clip.sequence(
        frame(imageIndex.set(3).then(move)),
        frame(imageIndex.set(4).then(move)),
        frame(imageIndex.set(5).then(move)),
        frame(imageIndex.set(6).then(move)),
        frame(imageIndex.set(7).then(move)),
        frame(imageIndex.set(0).then(move)),
        frame(imageIndex.set(1).then(move)),
        frame(imageIndex.set(2).then(move)))
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
