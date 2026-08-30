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

  public static Number constant(double d) {
    return Number.with(d);
  }

  public static NumberVariable variable(double d) {
    return NumberVariable.create(d);
  }

  public static Number number(DoubleSupplier supplier) {
    return Number.with(supplier);
  }

  public static final Number seconds =
    new Number() {
      float seconds;

      {
        add(() -> seconds += 1/60f);
      }

      @Override
      double get() {
        return seconds;
      }
    };

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
