package micapolos.tata8.model;

import java.util.ArrayList;
import java.util.List;

import static micapolos.tata8.Game.onUpdate;

public final class Game {
  static final List<Runnable> components = new ArrayList<>();

  static void add(Runnable runnable) {
    components.add(runnable);
  }

  public static final Mouse mouse = new Mouse();

  public static void show() {
    onUpdate = () -> {
      for (Runnable component : components) {
        component.run();
      }
    };
    micapolos.tata8.Game.start();
  }
}
