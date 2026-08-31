package micapolos.tata8.model;

import java.util.function.DoubleSupplier;

public class Number extends Component {
  DoubleSupplier supplier;
  double defaultValue;

  Number(boolean isVariable, DoubleSupplier supplier, double defaultValue) {
    super(isVariable);
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  double get() {
    DoubleSupplier supplier = this.supplier;
    return supplier != null ? supplier.getAsDouble() : defaultValue;
  }

  public static final Number seconds =
    new Number(false, null, 0) {
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

  public static Number random() {
    return number(Math::random);
  }

  public static final Number zero = number(0);

  public static Number number(double value) {
    return new Number(false, null, value);
  }

  public static Number number(DoubleSupplier aSupplier) {
    return new Number(false, aSupplier, 0);
  }

  public static Number variable() {
    return variable(0);
  }

  public static Number variable(double value) {
    return new Number(true, null, value) {
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

  public static Number variable(DoubleSupplier aSupplier) {
    return new Number(true, aSupplier, 0) {
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

  public Number minus(double x) {
    return number(() -> get() - x);
  }

  public Number minus(Number n) {
    return number(() -> get() - n.get());
  }

  public Number times(double x) {
    return number(() -> get() * x);
  }

  public Number times(Number n) {
    return number(() -> get() * n.get());
  }

  public Number fraction() {
    return number(() -> micapolos.tata8.Math.fract((float) get()));
  }

  public Integer integer() {
    return Integer.integer(() -> (int) get());
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
    checkVariable();
    return () -> init(x);
  }

  public Action set(Number number) {
    checkVariable();
    return () -> init(number);
  }

  public Action capture(Number number) {
    checkVariable();
    return () -> init(number.get());
  }

  public Action add(double d) {
    checkVariable();
    return () -> init(get() + d);
  }

  public Action add(Number n) {
    checkVariable();
    return () -> init(get() + n.get());
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    seconds.times(100).integer().show();
  }
}
