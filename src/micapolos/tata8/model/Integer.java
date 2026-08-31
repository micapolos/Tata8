package micapolos.tata8.model;

import micapolos.IntegerUtils;
import micapolos.tata8.Random;

import java.util.List;
import java.util.function.*;

import static micapolos.tata8.model.Bool.bool;
import static micapolos.tata8.model.Number.number;
import static micapolos.tata8.model.Value.value;

public class Integer extends Component {
  IntSupplier supplier;
  int defaultValue;

  Integer(boolean isVariable, IntSupplier supplier, int defaultValue) {
    super(isVariable);
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public int get() {
    IntSupplier supplier = this.supplier;
    return supplier != null ? supplier.getAsInt() : defaultValue;
  }

  public static Integer randomBetween(int min, int max) {
    return integer(() -> Random.between(min, max));
  }

  public static Integer randomUntil(int limit) {
    return integer(() -> Random.until(limit));
  }

  public static Integer zero = integer(0);
  public static Integer one = integer(1);

  public static Integer integer(int value) {
    return integer(() -> value);
  }

  public static Integer integer(IntSupplier aSupplier) {
    return new Integer(false, aSupplier, 0);
  }

  public static Integer variable() {
    return variable(0);
  }

  public static Integer variable(int value) {
    return variable(() -> value);
  }

  public static Integer variable(IntSupplier aSupplier) {
    return new Integer(true, aSupplier, 0) {
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

  public Integer update(IntUnaryOperator operator) {
    return integer(() -> operator.applyAsInt(get()));
  }

  public Integer update(Integer x, IntBinaryOperator operator) {
    return integer(() -> operator.applyAsInt(get(), x.get()));
  }

  public Number mapToNumber(IntToDoubleFunction function) {
    return number(() -> function.applyAsDouble(get()));
  }

  public Bool mapToBool(IntPredicate function) {
    return bool(() -> function.test(get()));
  }

  public <R> Value<R> mapToValue(IntFunction<R> function) {
    return value(() -> function.apply(get()));
  }

  public Integer negated() {
    return update(IntegerUtils::negated);
  }

  public Integer plus(int x) {
    return update(i -> i + x);
  }

  public Integer plus(Integer n) {
    return update(n, IntegerUtils::plus);
  }

  public Integer minus(int x) {
    return update(i -> i - x);
  }

  public Integer minus(Integer n) {
    return update(n, IntegerUtils::minus);
  }

  public Integer times(int x) {
    return update(i -> i * x);
  }

  public Integer times(Integer n) {
    return update(n, IntegerUtils::times);
  }

  public Number toNumber() {
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

  void init(Integer number) {
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

  public Action set(Integer number) {
    checkVariable();
    return () -> init(number);
  }

  public Action capture(Integer number) {
    checkVariable();
    return () -> init(number.get());
  }

  public Action add(int d) {
    checkVariable();
    return () -> init(get() + d);
  }

  public Action add(Integer n) {
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
