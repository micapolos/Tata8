package micapolos.tata8;

import java.util.function.BooleanSupplier;

public interface Event {
  boolean didHappen();

  static Event on(BooleanSupplier supplier) {
    return () -> supplier.getAsBoolean();
  }
}
