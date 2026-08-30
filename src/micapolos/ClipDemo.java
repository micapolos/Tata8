package micapolos;

import micapolos.tata8.model.Action;
import micapolos.tata8.model.Clip;
import micapolos.tata8.model.Number;

import static micapolos.tata8.Game.keys;
import static micapolos.tata8.Game.loadImage;
import static micapolos.tata8.model.Action.set;
import static micapolos.tata8.model.Clip.*;
import static micapolos.tata8.model.Event.any;
import static micapolos.tata8.model.Event.when;
import static micapolos.tata8.model.Game.constant;
import static micapolos.tata8.model.Game.newSprite;

public final class ClipDemo {
  static void main() {
    var images = loadImage(ClipDemo.class, "depressedChicken.png").sliceVertically(8);
    var sprite = newSprite();
    sprite.anchor.init(16, 0);
    sprite.position.init(50, 100);

    Number step = sprite.flip.x.select(constant(-3), constant(3));
    Action move = sprite.position.x.add(step);

    Clip walk = sequence(
      frame(set(sprite, images[3]).then(move)),
      frame(set(sprite, images[4]).then(move)),
      frame(set(sprite, images[5]).then(move)),
      frame(set(sprite, images[6]).then(move)),
      frame(set(sprite, images[7]).then(move)),
      frame(set(sprite, images[0]).then(move)),
      frame(set(sprite, images[1]).then(move)),
      frame(set(sprite, images[2]).then(move)))
      .stretch(0.2f)
      .repeat();

    Clip stand = instant(set(sprite, images[2]));

    Clip animate = stand.thenSelect(
      option(when(keys.right::pressed), instant(sprite.flip.x.set(false)).then(walk)),
      option(when(keys.left::pressed), instant(sprite.flip.x.set(true)).then(walk)),
      option(any(when(keys.right::released), when(keys.right::released)), stand));

    animate.show();
  }
}
