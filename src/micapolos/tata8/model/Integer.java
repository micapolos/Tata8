package micapolos.tata8.model;

import micapolos.IntegerUtils;
import micapolos.tata8.Random;

import java.util.List;
import java.util.function.*;

import static micapolos.tata8.model.Boolean.with;
import static micapolos.tata8.model.Number.with;
import static micapolos.tata8.model.Value.with;

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
    return with(() -> Random.between(min, max));
  }

  public static Integer randomUntil(int limit) {
    return with(() -> Random.until(limit));
  }

  public static Integer zero = with(0);
  public static Integer one = with(1);

  public static Integer with(int value) {
    return with(() -> value);
  }

  public static Integer with(IntSupplier aSupplier) {
    return new Integer(false, aSupplier, 0);
  }

  public static Integer newVariable() {
    return newVariable(0);
  }

  public static Integer newVariable(int value) {
    return newVariable(() -> value);
  }

  public static Integer newVariable(IntSupplier aSupplier) {
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
    return with(() -> operator.applyAsInt(get()));
  }

  public Integer update(Integer x, IntBinaryOperator operator) {
    return with(() -> operator.applyAsInt(get(), x.get()));
  }

  public Number mapToNumber(IntToDoubleFunction function) {
    return Number.with(() -> function.applyAsDouble(get()));
  }

  public Boolean mapToBool(IntPredicate function) {
    return Boolean.with(() -> function.test(get()));
  }

  public <R> Value<R> mapToValue(IntFunction<R> function) {
    return Value.with(() -> function.apply(get()));
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

  public Number number() {
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
