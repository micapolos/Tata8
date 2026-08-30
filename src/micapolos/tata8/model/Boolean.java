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

  static Boolean with(boolean value) {
    return new Boolean(false, null, value);
  }

  static Boolean with(BooleanSupplier supplier) {
    return new Boolean(false, supplier, false);
  }

  static Boolean variable() {
    return variable(false);
  }

  static Boolean variable(boolean value) {
    return new Boolean(true, null, value);
  }

  static Boolean variable(BooleanSupplier supplier) {
    return new Boolean(true, supplier, false);
  }

  public void set(boolean x) {
    set(null, x);
  }

  public void set(Boolean number) {
    set(number::get, false);
  }

  void set(BooleanSupplier supplier, boolean defaultValue) {
    if (!isVariable) {
      throw new IllegalArgumentException("Not a variable.");
    }
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    Boolean.with(false).show();
  }
}
