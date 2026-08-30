package micapolos.tata8.model;

import java.util.function.DoubleSupplier;

public abstract class Number implements Showable {
  abstract double get();

  static Number with(double d) {
    return new Number() {
      @Override
      double get() {
        return d;
      }
    };
  }

  static Number with(DoubleSupplier supplier) {
    return new Number() {
      @Override
      double get() {
        return supplier.getAsDouble();
      }
    };
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    new Number() {
      @Override
      double get() {
        return System.currentTimeMillis();
      }
    }.show();
  }
}
