package micapolos.tata8.model;

import java.util.ArrayList;
import java.util.List;

import static micapolos.tata8.Game.keys;
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

  public static final Event started = () -> startedValue;

  public static void show() {
    onUpdate = () -> {
      for (Runnable component : components) {
        component.run();
      }
      startedValue = keys.reset.pressed();
      if (startedValue) {
        secondsValue = 0;
      }
    };
    micapolos.tata8.Game.start();
  }

  public static void when(Event event, Action... actions) {
    add(() -> {
      if (event.didHappen()) {
        for (Action action : actions) {
          action.execute();
        }
      }
    });
  }

  static void main() {
    seconds.show();
  }
}
