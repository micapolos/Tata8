package micapolos;

import micapolos.tata8.model.Image;
import micapolos.tata8.model.Sprite;

public class Sandbox {
  static void main() {
    var images = Image.image(Chicken.class, "depressedChicken.png").sliceVertically(8);
    var sprite = Sprite.newSprite().with(images.get(0));
    sprite.show();
  }
}
