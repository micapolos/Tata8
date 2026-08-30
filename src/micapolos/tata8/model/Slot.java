package micapolos.tata8.model;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Slot<T> extends Value<T> {
  abstract void set(T value);

  public static <T> Slot<T> with(Supplier<T> supplier, Consumer<T> consumer) {
    return new Slot<T>() {
      @Override
      void set(T value) {
        consumer.accept(value);
      }

      @Override
      T get() {
        return supplier.get();
      }
    };
  }
}
