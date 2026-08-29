package micapolos;

import micapolos.tata8.Clip;
import micapolos.tata8.Game;

public final class ClipDemo {
  static void main() {
    var images = Game.loadImage(ClipDemo.class, "tilemap.png").slice(7, 11);
    var image = Game.loadImage(ClipDemo.class, "quote.png");
    var sprite = Game.newSprite();
    sprite.image = image;
    sprite.anchor.set(16, 0);
    sprite.position.set(0, 0);

    Clip clip =
      Clip.continuous(dt -> sprite.position.x += dt * 60).startWhen(Game.keys.right::isPressed);

    Game.add(clip);
    Game.start();
  }
}
