package micapolos;

import micapolos.tata8.model.Image;
import micapolos.tata8.model.Sprite;

import static micapolos.tata8.model.Clip.instant;

public class Sandbox {
  static void main() {
    var images = Image.image(Chicken.class, "depressedChicken.png").sliceVertically(8);
    var sprite = Sprite.create();
    var sprite2 = Sprite.create();
    instant(sprite.image.set(images[0])).show();
  }
}
