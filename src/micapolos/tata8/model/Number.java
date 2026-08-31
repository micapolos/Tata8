package micapolos.tata8.model;

import java.util.function.DoubleSupplier;

public class Number implements Showable {
  DoubleSupplier supplier;
  double defaultValue;

  Number(DoubleSupplier supplier, double defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  double get() {
    DoubleSupplier supplier = this.supplier;
    return supplier != null ? supplier.getAsDouble() : defaultValue;
  }

  public static Number seconds() {
    return new Number(null, 0) {
      {
        Game.add(new Clip() {
          @Override
          void start() {
            init(0);
          }

          @Override
          float advance(float seconds) {
            init(get() + seconds);
            return 0;
          }
        });
      }
    };
  }

  public static Number randomNumber() {
    return number(Math::random);
  }

  public static Number number() {
    return number(0);
  }

  public static Number number(double value) {
    return new Number(null, value) {
      {
        Game.add(new Clip() {
          @Override
          void start() {
            init(null, value);
          }

          @Override
          float advance(float seconds) {
            return 0;
          }
        });
      }
    };
  }

  public static Number number(DoubleSupplier aSupplier) {
    return new Number(aSupplier, 0) {
      {
        Game.add(new Clip() {
          @Override
          void start() {
            init(aSupplier, 0);
          }

          @Override
          float advance(float seconds) {
            return 0;
          }
        });
      }
    };
  }

  public Number plus(double x) {
    return number(() -> get() + x);
  }

  public Number plus(Number n) {
    return number(() -> get() + n.get());
  }

  public Number times(double x) {
    return number(() -> get() * x);
  }

  public Number times(Number n) {
    return number(() -> get() * n.get());
  }

  void init(double x) {
    init(null, x);
  }

  void init(Number number) {
    init(number::get, 0);
  }

  void init(DoubleSupplier supplier, double defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public Action set(double x) {
    return () -> init(x);
  }

  public Action set(Number number) {
    return () -> init(number);
  }

  public Action capture(Number number) {
    return () -> init(number.get());
  }

  public Action add(double d) {
    return () -> init(get() + d);
  }

  public Action add(Number n) {
    return () -> init(get() + n.get());
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    seconds().show();
  }
}
