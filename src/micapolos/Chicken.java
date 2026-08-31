package micapolos;

import micapolos.tata8.model.*;
import micapolos.tata8.model.Number;

import static micapolos.tata8.model.Bool.boolVariable;
import static micapolos.tata8.model.Clip.*;
import static micapolos.tata8.model.Event.any;
import static micapolos.tata8.model.Number.number;
import static micapolos.tata8.model.Number.variable;

final class Chicken {
  static final Image[] images = Image.image(Chicken.class, "depressedChicken.png").sliceVertically(8);

  final Sprite sprite = Sprite.sprite();
  final Number x = Number.variable(50);
  final Bool isLeft = boolVariable();
  final Clip clip;

  public Chicken() {
    Number step = isLeft.select(number(-3), number(3));
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

    Action stop = sprite.image.set(images[2]);

    Clip init = instant(
      stop,
      isLeft.set(false),
      x.set(50),
      sprite.anchor.set(16, 0),
      sprite.position.set(x, number(100)),
      sprite.flip.x.set(isLeft));

    clip = init.thenSelect(
      when(Key.RIGHT.pressed, instant(isLeft.set(false)).then(walk)),
      when(Key.LEFT.pressed, instant(isLeft.set(true)).then(walk)),
      when(any(Key.RIGHT.released, Key.LEFT.released), instant(stop)));
  }

  static void main() {
    new Chicken().clip.show();
  }
}
