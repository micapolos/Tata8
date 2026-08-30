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

  public static final Number zero = with(0);

  public static Number with(double value) {
    return new Number(false, null, value);
  }

  public static Number with(DoubleSupplier supplier) {
    return new Number(false, supplier, 0);
  }

  public static Number variable() {
    return variable(0);
  }

  public static Number variable(double value) {
    return new Number(true, null, value);
  }

  public static Number variable(DoubleSupplier supplier) {
    return new Number(true, supplier, 0);
  }

  public Number plus(double x) {
    return with(() -> get() + x);
  }

  public Number plus(Number n) {
    return with(() -> get() + n.get());
  }

  public Number times(double x) {
    return with(() -> get() * x);
  }

  public Number times(Number n) {
    return with(() -> get() * n.get());
  }

  public void init(double x) {
    init(null, x);
  }

  public void init(Number number) {
    init(number::get, 0);
  }

  void init(DoubleSupplier supplier, double defaultValue) {
    if (!isVariable) {
      throw new IllegalArgumentException("Not a variable.");
    }
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public Action set(double x) {
    return () -> init(null, x);
  }

  public Action set(Number number) {
    return () -> init(number::get, 0);
  }

  public Action add(double d) {
    return () -> init(get() + d);
  }

  public Action add(Number number) {
    return () -> init(get() + number.get());
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
