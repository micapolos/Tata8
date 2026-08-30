package micapolos;

import micapolos.tata8.Clip;

import static micapolos.tata8.Action.*;
import static micapolos.tata8.Clip.*;
import static micapolos.tata8.Event.when;
import static micapolos.tata8.Game.*;

public final class ClipDemo {
  static void main() {
    var images = loadImage(ClipDemo.class, "depressedChicken.png").sliceVertically(8);
    var sprite = newSprite();
    sprite.anchor.set(16, 0);
    sprite.position.set(50, 100);

    float step = 2.5f;
    Clip walk = sequence(
      frame(set(sprite, images[3]).then(moveX(sprite.position, step))),
      frame(set(sprite, images[4]).then(moveX(sprite.position, step))),
      frame(set(sprite, images[5]).then(moveX(sprite.position, step))),
      frame(set(sprite, images[6]).then(moveX(sprite.position, step))),
      frame(set(sprite, images[7]).then(moveX(sprite.position, step))),
      frame(set(sprite, images[0]).then(moveX(sprite.position, step))),
      frame(set(sprite, images[1]).then(moveX(sprite.position, step))),
      frame(set(sprite, images[2]).then(moveX(sprite.position, step))))
      .stretch(0.2f)
      .repeat();

    Clip stand = instant(set(sprite, images[2]));

    clip = stand.thenSelect(
      option(when(keys.right::pressed), walk),
      option(when(keys.right::released), stand));
    start();
  }
}
