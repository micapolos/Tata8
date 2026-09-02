package micapolos;

import micapolos.zexy.Image;
import micapolos.zexy.Sprite;

public class Sandbox {
  static void main() {
    var images = Image.image(Chicken.class, "depressedChicken.png").sliceVertically(8);
    var sprite = Sprite.newSprite().with(images.get(0));
    sprite.show();
  }
}
