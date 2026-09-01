package micapolos.tata8.model;

import micapolos.DoubleUtils;
import micapolos.tata8.model.live.LiveNumbers;

import java.util.function.*;

import static micapolos.tata8.Math.elastic;
import static micapolos.tata8.model.Boolean.with;
import static micapolos.tata8.model.Value.with;

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

  public static Number random() {
    return with(Math::random);
  }

  public static final Number zero = with(0);
  public static final Number half = with(0.5f);
  public static final Number one = with(1);

  public static Number with(double value) {
    return new Number(false, null, value);
  }

  public static Number with(DoubleSupplier supplier) {
    return new Number(false, supplier, 0);
  }

  public static Number newVariable() {
    return newVariable(0);
  }

  public static Number newVariable(double value) {
    return newVariable(() -> value);
  }

  public static Number newVariable(Number value) {
    return newVariable(value::get);
  }

  public static Number newVariable(DoubleSupplier aSupplier) {
    var number = new Number(true, aSupplier, 0);
    Game.addInit(number.initialize);
    return number;
  }

  public Number readonly() {
    return isVariable ? with(this::get) : this;
  }

  public Number update(DoubleUnaryOperator operator) {
    return with(() -> operator.applyAsDouble(get()));
  }

  public Number update(Number b, DoubleBinaryOperator operator) {
    return with(() -> operator.applyAsDouble(get(), b.get()));
  }

  public <R> Value<R> mapToValue(DoubleFunction<R> function) {
    return Value.with(() -> function.apply(get()));
  }

  public Integer mapToInteger(DoubleToIntFunction function) {
    return Integer.with(() -> function.applyAsInt(get()));
  }

  public Number mapToNumber(DoubleUnaryOperator function) {
    return with(() -> function.applyAsDouble(get()));
  }

  public Boolean mapToBool(DoublePredicate function) {
    return Boolean.with(() -> function.test(get()));
  }

  public Number negated() {
    return update(DoubleUtils::negated);
  }

  public Number plus(double x) {
    return update(d -> d + x);
  }

  public Number plus(Number n) {
    return update(n, DoubleUtils::plus);
  }

  public Number minus(double x) {
    return update(d -> d - x);
  }

  public Number minus(Number n) {
    return update(n, DoubleUtils::minus);
  }

  public Number times(double x) {
    return update(d -> d * x);
  }

  public Number times(Number n) {
    return update(n, DoubleUtils::times);
  }

  public Number fraction() {
    return update(DoubleUtils::fraction);
  }

  public Integer integer() {
    return mapToInteger(d -> (int) d);
  }

  public void setImmediately(double x) {
    setImmediately(null, x);
  }

  public void setImmediately(Number number) {
    setImmediately(number::get, 0);
  }

  void setImmediately(DoubleSupplier supplier, double defaultValue) {
    checkVariable();
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
    return setElastic(with(n));
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
    LiveNumbers.liveSeconds().map(Number::integer).show();
  }
}
