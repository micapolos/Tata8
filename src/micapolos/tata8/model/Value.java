package micapolos.tata8.model;

import java.util.function.Supplier;

public final class Value<T> implements Showable {
  final boolean isFinal;
  Supplier<T> supplier;
  T defaultValue;

  Value(boolean isFinal, Supplier<T> supplier, T defaultValue) {
    this.isFinal = isFinal;
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  T get() {
    var supplier = this.supplier;
    return supplier != null ? supplier.get() : defaultValue;
  }

  static <T> Value<T> with(T value) {
    return new Value<>(true, null, value);
  }

  static <T> Value<T> with(Supplier<T> supplier) {
    return new Value<>(true, supplier, null);
  }

  static <T> Value<T> variable() {
    return new Value<>(false, null, null);
  }

  public void set(T value) {
    set(null, value);
  }

  public void set(Value<T> value) {
    set(value::get, null);
  }

  void set(Supplier<T> supplier, T defaultValue) {
    if (isFinal) {
      throw new IllegalArgumentException("final");
    }
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    long now = System.currentTimeMillis();
    Value.with(() -> (int) (System.currentTimeMillis() - now)).show();
  }
}
