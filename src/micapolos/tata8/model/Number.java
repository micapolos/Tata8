package micapolos.tata8.model;

import java.util.function.DoubleSupplier;

public final class Number implements Showable {
  final boolean isVariable;
  DoubleSupplier supplier;
  double defaultValue;

  Number(boolean isVariable, DoubleSupplier supplier, double defaultValue) {
    this.isVariable = isVariable;
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  double get() {
    DoubleSupplier supplier = this.supplier;
    return supplier != null ? supplier.getAsDouble() : defaultValue;
  }

  static final Number zero = with(0);

  static Number with(double value) {
    return new Number(false, null, value);
  }

  static Number with(DoubleSupplier supplier) {
    return new Number(false, supplier, 0);
  }

  static Number variable() {
    return variable(0);
  }

  static Number variable(double value) {
    return new Number(true, null, value);
  }

  static Number variable(DoubleSupplier supplier) {
    return new Number(true, supplier, 0);
  }

  public void set(double x) {
    set(null, x);
  }

  public void set(Number number) {
    set(number::get, 0);
  }

  void set(DoubleSupplier supplier, double defaultValue) {
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
    long now = System.currentTimeMillis();
    Number.with(() -> (int) (System.currentTimeMillis() - now)).show();
  }
}
