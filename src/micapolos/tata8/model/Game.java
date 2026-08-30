package micapolos.tata8.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleSupplier;

import static micapolos.tata8.Game.loadImage;
import static micapolos.tata8.Game.onUpdate;

public final class Game {
  static final List<Runnable> components = new ArrayList<>();

  static void add(Runnable runnable) {
    components.add(runnable);
  }

  public static Number zero = constant(0);

  public static Number constant(double d) {
    return Number.with(d);
  }

  static double secondsValue;
  {
    add(() -> secondsValue += 1/60f);
  }

  public static final Number seconds = Number.with(() -> secondsValue);

  public static final Mouse mouse = new Mouse();

  public static Sprite newSprite() {
    Sprite sprite = new Sprite(micapolos.tata8.Game.newSprite());
    add(sprite::sync);
    return sprite;
  }

  static void start() {
    onUpdate = () -> {
      for (Runnable component : components) {
        component.run();
      }
    };
    micapolos.tata8.Game.start();
  }

  static void main() {
    Sprite sprite = newSprite();
    sprite.image.set(loadImage(Game.class, "depressedChicken.png"));
    sprite.position.x.set(seconds);
    start();
  }
}
