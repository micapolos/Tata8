package micapolos.tata8.model;

import java.util.function.BooleanSupplier;

public final class Boolean implements Showable {
  final boolean isVariable;
  BooleanSupplier supplier;
  boolean defaultValue;

  Boolean(boolean isVariable, BooleanSupplier supplier, boolean defaultValue) {
    this.isVariable = isVariable;
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  boolean get() {
    BooleanSupplier supplier = this.supplier;
    return supplier != null ? supplier.getAsBoolean() : defaultValue;
  }

  static final Boolean YES = with(true);
  static final Boolean NO = with(false);

  public static Boolean with(boolean value) {
    return new Boolean(false, null, value);
  }

  public static Boolean with(BooleanSupplier supplier) {
    return new Boolean(false, supplier, false);
  }

  public static Boolean variable() {
    return variable(false);
  }

  public static Boolean variable(boolean value) {
    return new Boolean(true, null, value);
  }

  public static Boolean variable(BooleanSupplier supplier) {
    return new Boolean(true, supplier, false);
  }

  public Action set(boolean x) {
    return set(null, x);
  }

  public Action set(Boolean number) {
    return set(number::get, false);
  }

  Action set(BooleanSupplier supplier, boolean defaultValue) {
    if (!isVariable) {
      throw new IllegalArgumentException("Not a variable.");
    }
    return () -> {
      this.supplier = supplier;
      this.defaultValue = defaultValue;
    };
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
