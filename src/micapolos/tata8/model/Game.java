package micapolos.tata8.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleSupplier;

import static micapolos.tata8.Game.onUpdate;

public final class Game {
  static final List<Runnable> components = new ArrayList<>();

  static void add(Runnable runnable) {
    components.add(runnable);
  }

  public static Number zero = with(0);

  public static Number with(double d) {
    return Number.with(d);
  }

  public static Number number(DoubleSupplier supplier) {
    return Number.with(supplier);
  }

  static double secondsValue;
  {
    add(() -> secondsValue += 1/60f);
  }

  public static final Number seconds = Number.with(() -> secondsValue);

  public static final Mouse mouse = new Mouse();

  static void start() {
    onUpdate = () -> {
      for (Runnable component : components) {
        component.run();
      }
    };
    micapolos.tata8.Game.start();
  }
}
