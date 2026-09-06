package micapolos;

import micapolos.tata8.Game;
import micapolos.tata8.Image;

public final class DepressedChicken {
  public static final Image image = Game.loadImage(DepressedChicken.class, "depressedChicken.png");
  public static final Image[] images = image.sliceVertically(8);
}
