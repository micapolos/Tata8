package micapolos.tata8.model;

import java.util.function.*;

import static micapolos.tata8.Math.elastic;
import static micapolos.tata8.Math.fract;
import static micapolos.tata8.model.Bool.bool;
import static micapolos.tata8.model.Clipped.clipped;
import static micapolos.tata8.model.Value.value;

public class Number extends Component {
  DoubleSupplier supplier;
  double defaultValue;

  Number(boolean isVariable, DoubleSupplier supplier, double defaultValue) {
    super(isVariable);
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public double get() {
    DoubleSupplier supplier = this.supplier;
    return supplier != null ? supplier.getAsDouble() : defaultValue;
  }

  public static Clipped<Number> clippedSeconds() {
    Number number = Number.variable();
    Clip clip = new Clip() {
      @Override
      void start() {
        number.setImmediately(0);
      }

      @Override
      float step(float seconds) {
        number.setImmediately(number.get() + seconds);
        return 0;
      }
    };
    return clipped(number, clip);
  }

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

  public Number update(DoubleUnaryOperator operator) {
    return number(() -> operator.applyAsDouble(get()));
  }

  public <R> Value<R> mapToValue(DoubleFunction<R> function) {
    return value(() -> function.apply(get()));
  }

  public Integer mapToInteger(DoubleToIntFunction function) {
    return Integer.integer(() -> function.applyAsInt(get()));
  }

  public Number mapToNumber(DoubleUnaryOperator function) {
    return number(() -> function.applyAsDouble(get()));
  }

  public Bool mapToBool(DoublePredicate function) {
    return bool(() -> function.test(get()));
  }

  public Number negated() {
    return update(d -> -d);
  }

  public Number plus(double x) {
    return update(d -> d + x);
  }

  public Number plus(Number n) {
    return update(d -> d + n.get());
  }

  public Number minus(double x) {
    return update(d -> d - x);
  }

  public Number minus(Number n) {
    return update(d -> d - n.get());
  }

  public Number times(double x) {
    return update(d -> d * x);
  }

  public Number times(Number n) {
    return update(d -> d * n.get());
  }

  public Number fraction() {
    return update(d -> fract((float) get()));
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

  public Stepper setElastic(double n) {
    return setElastic(number(n));
  }

  public Stepper setElastic(Number number) {
    checkVariable();
    return seconds -> {
      setImmediately(elastic((float) get(), (float) number.get()));
      return seconds;
    };
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
    clippedSeconds().map(Number::integer).show();
  }
}
