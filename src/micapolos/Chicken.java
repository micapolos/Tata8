package micapolos;

import micapolos.tata8.model.*;
import micapolos.tata8.model.Number;

import static micapolos.tata8.model.Clip.*;
import static micapolos.tata8.model.Event.any;
import static micapolos.tata8.model.Number.number;

final class Chicken {
  static final Image[] images = Image.image(Chicken.class, "depressedChicken.png").sliceVertically(8);

  final Sprite sprite = Sprite.create();
  final Position position = Position.variable();
  final Bool isLeft = Bool.variable();
  final Clip clip;

  public Chicken() {
    Number step = isLeft.select(number(-3), number(3));
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
        select(when(Game.mouse.press, instant(position.capture(Game.mouse.position)))),
        select(
          when(Key.RIGHT.pressed, instant(isLeft.set(false)).then(walk)),
          when(Key.LEFT.pressed, instant(isLeft.set(true)).then(walk)),
          when(any(Key.RIGHT.released, Key.LEFT.released), instant(stop)))));
  }

  static void main() {
    new Chicken().clip.show();
  }
}
