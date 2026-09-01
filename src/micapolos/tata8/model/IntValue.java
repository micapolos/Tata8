package micapolos.tata8.model;

import micapolos.IntegerUtils;
import micapolos.tata8.Random;

import java.util.List;
import java.util.function.*;

import static micapolos.tata8.model.BooleanValue.with;
import static micapolos.tata8.model.DoubleValue.with;
import static micapolos.tata8.model.Value.with;

public class IntValue extends Component {
  IntSupplier supplier;
  int defaultValue;

  IntValue(boolean isVariable, IntSupplier supplier, int defaultValue) {
    super(isVariable);
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public int get() {
    IntSupplier supplier = this.supplier;
    return supplier != null ? supplier.getAsInt() : defaultValue;
  }

  public static IntValue randomBetween(int min, int max) {
    return with(() -> Random.between(min, max));
  }

  public static IntValue randomUntil(int limit) {
    return with(() -> Random.until(limit));
  }

  public static IntValue zero = with(0);
  public static IntValue one = with(1);

  public static IntValue with(int value) {
    return with(() -> value);
  }

  public static IntValue with(IntSupplier aSupplier) {
    return new IntValue(false, aSupplier, 0);
  }

  public static IntValue newVariable() {
    return newVariable(0);
  }

  public static IntValue newVariable(int value) {
    return newVariable(() -> value);
  }

  public static IntValue newVariable(IntSupplier aSupplier) {
    return new IntValue(true, aSupplier, 0) {
      {
        Game.add(new Clip() {
          @Override
          void start() {
            init(aSupplier, 0);
          }

          @Override
          float step(float seconds) {
            return 0;
          }
        });
      }
    };
  }

  public IntValue update(IntUnaryOperator operator) {
    return with(() -> operator.applyAsInt(get()));
  }

  public IntValue update(IntValue x, IntBinaryOperator operator) {
    return with(() -> operator.applyAsInt(get(), x.get()));
  }

  public DoubleValue mapToNumber(IntToDoubleFunction function) {
    return DoubleValue.with(() -> function.applyAsDouble(get()));
  }

  public BooleanValue mapToBool(IntPredicate function) {
    return BooleanValue.with(() -> function.test(get()));
  }

  public <R> Value<R> mapToValue(IntFunction<R> function) {
    return Value.with(() -> function.apply(get()));
  }

  public IntValue negated() {
    return update(IntegerUtils::negated);
  }

  public IntValue plus(int x) {
    return update(i -> i + x);
  }

  public IntValue plus(IntValue n) {
    return update(n, IntegerUtils::plus);
  }

  public IntValue minus(int x) {
    return update(i -> i - x);
  }

  public IntValue minus(IntValue n) {
    return update(n, IntegerUtils::minus);
  }

  public IntValue times(int x) {
    return update(i -> i * x);
  }

  public IntValue times(IntValue n) {
    return update(n, IntegerUtils::times);
  }

  public DoubleValue toNumber() {
    return mapToNumber(IntegerUtils::toDouble);
  }

  public <T> Value<T> getValue(T... values) {
    return mapToValue(i -> values[Math.floorMod(i, values.length)]);
  }

  public <T> Value<T> get(List<T> values) {
    return mapToValue(i -> values.get(Math.floorMod(i, values.size())));
  }

  void init(int x) {
    init(null, x);
  }

  void init(IntValue number) {
    init(number::get, 0);
  }

  void init(IntSupplier supplier, int defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public Action set(int x) {
    checkVariable();
    return () -> init(x);
  }

  public Action set(IntValue number) {
    checkVariable();
    return () -> init(number);
  }

  public Action capture(IntValue number) {
    checkVariable();
    return () -> init(number.get());
  }

  public Action add(int d) {
    checkVariable();
    return () -> init(get() + d);
  }

  public Action add(IntValue n) {
    checkVariable();
    return () -> init(get() + n.get());
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    randomUntil(10).show();
  }
}
