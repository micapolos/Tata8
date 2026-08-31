package micapolos;

import micapolos.tata8.model.*;
import micapolos.tata8.model.Number;

import static micapolos.tata8.model.Clip.*;
import static micapolos.tata8.model.Clip.instant;
import static micapolos.tata8.model.Clip.on;
import static micapolos.tata8.model.Event.any;
import static micapolos.tata8.model.Number.number;

final class Chicken {
  static final Image[] images = Image.image(Chicken.class, "depressedChicken.png").sliceVertically(8);

  final Sprite sprite = Sprite.newSprite();
  final Position position = Position.variable();
  final Bool isLeft = Bool.variable();
  final Clip clip;

  public Chicken() {
    Number speed = Key.Z.isPressed.select(number(2), number(1));
    Number step = isLeft.select(speed.negated().times(3), speed.times(3));
    Action move = position.x.add(step);

    Clip walk = sequence(
      frame(sprite.image.set(images[3]).then(move)),
      frame(sprite.image.set(images[4]).then(move)),
      frame(sprite.image.set(images[5]).then(move)),
      frame(sprite.image.set(images[6]).then(move)),
      frame(sprite.image.set(images[7]).then(move)),
      frame(sprite.image.set(images[0]).then(move)),
      frame(sprite.image.set(images[1]).then(move)),
      frame(sprite.image.set(images[2]).then(move)))
      .stretch(0.1f)
      .repeat();

    Action stop = sprite.image.set(images[2]);

    Clip init = instant(
      stop,
      position.set(50, 100),
      isLeft.set(false),
      sprite.anchor.set(16, 0),
      sprite.position.set(position),
      sprite.flip.x.set(isLeft));

    clip = init.then(
      parallel(
        select(on(Game.mouse.press, instant(position.capture(Game.mouse.position)))),
        select(
          on(Key.RIGHT.press, instant(isLeft.set(false)).then(walk)),
          on(Key.LEFT.press, instant(isLeft.set(true)).then(walk)),
          on(any(Key.RIGHT.release, Key.LEFT.release), instant(stop)))));
  }

  static void main() {
    new Chicken().clip.show();
  }
}
