package micapolos;

import micapolos.tata8.Image;
import micapolos.tata8.model.*;
import micapolos.tata8.model.Number;

import static micapolos.tata8.Game.loadImage;
import static micapolos.tata8.model.Bool.bool;
import static micapolos.tata8.model.Clip.*;
import static micapolos.tata8.model.Event.any;
import static micapolos.tata8.model.Game.started;
import static micapolos.tata8.model.Number.number;
import static micapolos.tata8.model.Sprite.sprite;

final class Chicken {
  static final Image[] images = loadImage(Chicken.class, "depressedChicken.png").sliceVertically(8);

  final Sprite sprite = sprite();
  final Number x = number(50);
  final Bool isLeft = bool();
  final Clip clip;

  public Chicken() {
    Number step = isLeft.select(number(-3), number(3));
    Action flipLeft = isLeft.set(true);
    Action flipRight = isLeft.set(false);
    Action move = x.add(step);

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

    Clip stand = instant(sprite.image.set(images[2]));

    clip = stand.thenSelect(
      when(started,
        instant(
          isLeft.set(false),
          x.set(50),
          sprite.anchor.set(16, 0),
          sprite.position.set(x, number(100)),
          sprite.flip.x.set(isLeft))),
      when(Key.RIGHT.pressed, instant(flipRight).then(walk)),
      when(Key.LEFT.pressed, instant(flipLeft).then(walk)),
      when(any(Key.RIGHT.released, Key.LEFT.released), stand));
  }

  static void main() {
    new Chicken().clip.show();
  }
}
