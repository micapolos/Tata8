package micapolos.tata8.model;

import java.util.function.BooleanSupplier;

import static micapolos.tata8.Game.*;

public interface Condition {
  boolean isHappening();

  Condition ALWAYS = () -> true;

  static Condition onlyIf(BooleanSupplier supplier) {
    return supplier::getAsBoolean;
  }

  static Condition all(Condition... conditions) {
    return () -> {
      boolean all = true;
      for (Condition condition : conditions) {
        all = all && condition.isHappening();
      }
      return all;
    };
  }

  static Condition any(Condition... conditions) {
    return () -> {
      boolean any = false;
      for (Condition condition : conditions) {
        any = any || condition.isHappening();
      }
      return any;
    };
  }

  static Condition not(Condition condition) {
    return () -> !condition.isHappening();
  }

  default void show() {
    onUpdate = () -> log(isHappening());
    start();
  }
}

