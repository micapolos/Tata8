package micapolos;

import static micapolos.tata8.Game.loadImage;
import static micapolos.tata8.model.Game.*;
import static micapolos.tata8.model.Sprite.sprite;

public class Demko {
  static void main() {
    var images = loadImage(Chicken.class, "depressedChicken.png").sliceVertically(8);
    var sprite = sprite();
    when(started,
      sprite.image.setRandomOf(images),
      sprite.position.x.set(seconds.times(60)));
    sprite.show();
  }
}
