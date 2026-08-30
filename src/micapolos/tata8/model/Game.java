package micapolos.tata8.model;

import java.util.ArrayList;
import java.util.List;

import static micapolos.tata8.Game.onUpdate;

public final class Game {
  static final List<Runnable> components = new ArrayList<>();

  static void add(Runnable runnable) {
    components.add(runnable);
  }

  static boolean startedValue = true;
  static double secondsValue;

  static {
    add(() -> secondsValue += 1/60f);
  }

  public static final Number seconds = Number.number(() -> secondsValue);

  public static final Mouse mouse = new Mouse();

  public static final Event  started = () -> startedValue;

  static void show() {
    onUpdate = () -> {
      for (Runnable component : components) {
        component.run();
      }
      startedValue = false;
    };
    micapolos.tata8.Game.start();
  }

  static void main() {
    seconds.show();
  }
}
