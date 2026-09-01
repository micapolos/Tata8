package micapolos.tata8.model;

import micapolos.IntegerUtils;
import micapolos.tata8.Random;

import java.util.List;
import java.util.function.*;

import static micapolos.tata8.model.BooleanValue.bool;
import static micapolos.tata8.model.DoubleValue.number;
import static micapolos.tata8.model.Value.value;

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
    return integer(() -> Random.between(min, max));
  }

  public static IntValue randomUntil(int limit) {
    return integer(() -> Random.until(limit));
  }

  public static IntValue zero = integer(0);
  public static IntValue one = integer(1);

  public static IntValue integer(int value) {
    return integer(() -> value);
  }

  public static IntValue integer(IntSupplier aSupplier) {
    return new IntValue(false, aSupplier, 0);
  }

  public static IntValue variable() {
    return variable(0);
  }

  public static IntValue variable(int value) {
    return variable(() -> value);
  }

  public static IntValue variable(IntSupplier aSupplier) {
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
    return integer(() -> operator.applyAsInt(get()));
  }

  public IntValue update(IntValue x, IntBinaryOperator operator) {
    return integer(() -> operator.applyAsInt(get(), x.get()));
  }

  public DoubleValue mapToNumber(IntToDoubleFunction function) {
    return number(() -> function.applyAsDouble(get()));
  }

  public BooleanValue mapToBool(IntPredicate function) {
    return bool(() -> function.test(get()));
  }

  public <R> Value<R> mapToValue(IntFunction<R> function) {
    return value(() -> function.apply(get()));
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
