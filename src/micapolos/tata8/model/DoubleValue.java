package micapolos.tata8.model;

import micapolos.DoubleUtils;

import java.util.function.*;

import static micapolos.tata8.Math.elastic;
import static micapolos.tata8.model.BooleanValue.bool;
import static micapolos.tata8.model.Clipped.clipped;
import static micapolos.tata8.model.Value.value;

public class DoubleValue extends Component {
  DoubleSupplier supplier;
  double defaultValue;

  DoubleValue(boolean isVariable, DoubleSupplier supplier, double defaultValue) {
    super(isVariable);
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public double get() {
    DoubleSupplier supplier = this.supplier;
    return supplier != null ? supplier.getAsDouble() : defaultValue;
  }

  public static Clipped<DoubleValue> clippedSeconds() {
    DoubleValue doubleValue = DoubleValue.variable();
    Clip clip = new Clip() {
      @Override
      void start() {
        doubleValue.setImmediately(0);
      }

      @Override
      float step(float seconds) {
        doubleValue.setImmediately(doubleValue.get() + seconds);
        return 0;
      }
    };
    return clipped(doubleValue, clip);
  }

  public static DoubleValue random() {
    return number(Math::random);
  }

  public static final DoubleValue zero = number(0);
  public static final DoubleValue half = number(0.5f);
  public static final DoubleValue one = number(1);

  public static DoubleValue number(double value) {
    return new DoubleValue(false, null, value);
  }

  public static DoubleValue number(DoubleSupplier supplier) {
    return new DoubleValue(false, supplier, 0);
  }

  public static DoubleValue variable() {
    return variable(0);
  }

  public static DoubleValue variable(double value) {
    return variable(() -> value);
  }

  public static DoubleValue variable(DoubleValue value) {
    return variable(value::get);
  }

  public static DoubleValue variable(DoubleSupplier aSupplier) {
    var number = new DoubleValue(true, aSupplier, 0);
    Game.addInit(number.initialize);
    return number;
  }

  public DoubleValue readonly() {
    return isVariable ? number(this::get) : this;
  }

  public DoubleValue update(DoubleUnaryOperator operator) {
    return number(() -> operator.applyAsDouble(get()));
  }

  public DoubleValue update(DoubleValue b, DoubleBinaryOperator operator) {
    return number(() -> operator.applyAsDouble(get(), b.get()));
  }

  public <R> Value<R> mapToValue(DoubleFunction<R> function) {
    return value(() -> function.apply(get()));
  }

  public IntValue mapToInteger(DoubleToIntFunction function) {
    return IntValue.integer(() -> function.applyAsInt(get()));
  }

  public DoubleValue mapToNumber(DoubleUnaryOperator function) {
    return number(() -> function.applyAsDouble(get()));
  }

  public BooleanValue mapToBool(DoublePredicate function) {
    return bool(() -> function.test(get()));
  }

  public DoubleValue negated() {
    return update(DoubleUtils::negated);
  }

  public DoubleValue plus(double x) {
    return update(d -> d + x);
  }

  public DoubleValue plus(DoubleValue n) {
    return update(n, DoubleUtils::plus);
  }

  public DoubleValue minus(double x) {
    return update(d -> d - x);
  }

  public DoubleValue minus(DoubleValue n) {
    return update(n, DoubleUtils::minus);
  }

  public DoubleValue times(double x) {
    return update(d -> d * x);
  }

  public DoubleValue times(DoubleValue n) {
    return update(n, DoubleUtils::times);
  }

  public DoubleValue fraction() {
    return update(DoubleUtils::fraction);
  }

  public IntValue integer() {
    return mapToInteger(d -> (int) d);
  }

  public void setImmediately(double x) {
    setImmediately(null, x);
  }

  public void setImmediately(DoubleValue doubleValue) {
    setImmediately(doubleValue::get, 0);
  }

  void setImmediately(DoubleSupplier supplier, double defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public void init(double x) {
    init(() -> setImmediately(x));
  }

  public void init(DoubleValue doubleValue) {
    init(() -> setImmediately(doubleValue));
  }

  public Action set(double x) {
    checkVariable();
    return () -> setImmediately(x);
  }

  public Action set(DoubleValue doubleValue) {
    checkVariable();
    return () -> setImmediately(doubleValue);
  }

  public Action capture(DoubleValue doubleValue) {
    checkVariable();
    return () -> setImmediately(doubleValue.get());
  }

  public Stepper setElastic(double n) {
    return setElastic(number(n));
  }

  public Stepper setElastic(DoubleValue doubleValue) {
    checkVariable();
    return seconds -> {
      setImmediately(elastic((float) get(), (float) doubleValue.get()));
      return seconds;
    };
  }

  public Action add(double d) {
    checkVariable();
    return () -> setImmediately(get() + d);
  }

  public Action add(DoubleValue n) {
    checkVariable();
    return () -> setImmediately(get() + n.get());
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    clippedSeconds().map(DoubleValue::integer).show();
  }
}
