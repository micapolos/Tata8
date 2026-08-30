package micapolos.tata8.model;

import micapolos.tata8.Random;

import java.util.function.Supplier;

public final class Value<T> implements Showable {
  Supplier<T> supplier;
  T defaultValue;

  Value(Supplier<T> supplier, T defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  T get() {
    var supplier = this.supplier;
    return supplier != null ? supplier.get() : defaultValue;
  }

  static <T> Value<T> value() {
    return new Value<>(null, null);
  }

  static <T> Value<T> value(T value) {
    return new Value<>(null, value);
  }

  static <T> Value<T> value(Supplier<T> supplier) {
    return new Value<>(supplier, null);
  }

  void init(T value) {
    init(null, value);
  }

  void init(Value<T> value) {
    init(value::get, null);
  }

  void init(Supplier<T> supplier, T defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public Action set(T value) {
    return () -> init(value);
  }

  public Action set(Value<T> value) {
    return () -> init(value);
  }

  public Action setRandomOf(T... values) {
    return () -> init(values[Random.until(values.length)]);
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    long now = System.currentTimeMillis();
    Value.value(() -> (int) (System.currentTimeMillis() - now)).show();
  }
}
