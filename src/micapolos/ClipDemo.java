package micapolos;

import static micapolos.tata8.Animation.*;
import static micapolos.tata8.Clip.*;
import static micapolos.tata8.Game.*;

public final class ClipDemo {
  static void main() {
    var images = loadImage(ClipDemo.class, "tilemap.png").slice(7, 11);
    var image = loadImage(ClipDemo.class, "quote.png");
    var sprite = newSprite();
    sprite.image = image;
    sprite.anchor.set(16, 0);
    sprite.position.set(0, 0);

    clip = with(movingX(sprite.position, 60f));
    start();
  }
}
