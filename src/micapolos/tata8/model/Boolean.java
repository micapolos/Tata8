package micapolos.tata8.model;

import java.util.function.BooleanSupplier;

public final class Boolean implements Showable {
  BooleanSupplier supplier;
  boolean defaultValue;

  Boolean(BooleanSupplier supplier, boolean defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  boolean get() {
    BooleanSupplier supplier = this.supplier;
    return supplier != null ? supplier.getAsBoolean() : defaultValue;
  }

  public static Boolean with(boolean value) {
    return new Boolean(null, value);
  }

  public static Boolean with(BooleanSupplier supplier) {
    return new Boolean(supplier, false);
  }

  public void set(boolean x) {
    set(null, x);
  }

  public void set(Boolean number) {
    set(number::get, false);
  }

  void set(BooleanSupplier supplier, boolean defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public <T> Value<T> select(Value<T> trueValue, Value<T> falseValue) {
    return Value.with(() -> get() ? trueValue.get() : falseValue.get());
  }

  public Number select(Number trueNumber, Number falseNumber) {
    return Number.with(() -> get() ? trueNumber.get() : falseNumber.get());
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    Boolean.with(false).show();
  }
}
