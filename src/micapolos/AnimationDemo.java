package micapolos;

import micapolos.tata8.Animation;
import micapolos.tata8.Game;

public final class AnimationDemo {
  static void main() {
    var images = Game.loadImage(AnimationDemo.class, "tilemap.png").slice(7, 11);
    var image = Game.loadImage(AnimationDemo.class, "quote.png");
    var sprite = Game.newSprite();
    sprite.image = image;
    sprite.anchor.set(16, 0);
    sprite.position.set(0, 0);

    Animation animation =
      Animation.continuous(dt -> sprite.position.x += dt * 60).startWhen(Game.keys.right::isPressed);

    Game.add(animation);
    Game.start();
  }
}
