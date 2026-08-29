package micapolos;

import micapolos.tata8.Animation;
import micapolos.tata8.Game;

import static micapolos.tata8.Duration.seconds;

public final class AnimationDemo {
  static void main() {
    var images = Game.loadImage(AnimationDemo.class, "tilemap.png").sliceVertically(7);
    var sprite = Game.newSprite();

    Animation animation =
        Animation.random(
            Animation.set(sprite, images[0]).then(Animation.pause(seconds(1))),
            Animation.set(sprite, images[1]).then(Animation.pause(seconds(1))),
            Animation.set(sprite, images[2]).then(Animation.pause(seconds(1))),
            Animation.set(sprite, images[3]).then(Animation.pause(seconds(1))),
            Animation.set(sprite, images[4]).then(Animation.pause(seconds(1))),
            Animation.set(sprite, images[5]).then(Animation.pause(seconds(1))),
            Animation.set(sprite, images[6]).then(Animation.pause(seconds(1)))).startWhen(Game.keys.z::didPress);

    Game.add(animation);

    Game.start();
  }
}
