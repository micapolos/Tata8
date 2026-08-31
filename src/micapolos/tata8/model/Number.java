package micapolos.tata8.model;

import java.util.function.DoubleSupplier;

import static micapolos.tata8.Math.elastic;

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
        Game.addInit(() -> setImmediately(0));
        Game.add(seconds -> setImmediately(get() + seconds));
      }
    };

  public static Number random() {
    return number(Math::random);
  }

  public static final Number zero = number(0);
  public static final Number half = number(0.5f);
  public static final Number one = number(1);

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
    return variable(() -> value);
  }

  public static Number variable(DoubleSupplier aSupplier) {
    var number = new Number(true, aSupplier, 0);
    Game.addInit(() -> number.setImmediately(aSupplier, 0));
    return number;
  }

  public Number negated() {
    return number(() -> -get());
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

  void setImmediately(double x) {
    setImmediately(null, x);
  }

  void setImmediately(Number number) {
    setImmediately(number::get, 0);
  }

  void setImmediately(DoubleSupplier supplier, double defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public void init(double x) {
    init(() -> setImmediately(x));
  }

  public void init(Number number) {
    init(() -> setImmediately(number));
  }

  public Action set(double x) {
    checkVariable();
    return () -> setImmediately(x);
  }

  public Action set(Number number) {
    checkVariable();
    return () -> setImmediately(number);
  }

  public Action capture(Number number) {
    checkVariable();
    return () -> setImmediately(number.get());
  }

  public Animation setElastic(double n) {
    return setElastic(number(n));
  }

  public Animation setElastic(Number number) {
    checkVariable();
    return seconds -> setImmediately(elastic((float) get(), (float) number.get()));
  }

  public Action add(double d) {
    checkVariable();
    return () -> setImmediately(get() + d);
  }

  public Action add(Number n) {
    checkVariable();
    return () -> setImmediately(get() + n.get());
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    seconds.integer().show();
  }
}
