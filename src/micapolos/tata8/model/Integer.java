package micapolos.tata8.model;

import micapolos.tata8.Random;

import java.util.function.*;

import static micapolos.tata8.model.Bool.bool;
import static micapolos.tata8.model.Value.value;

public class Integer extends Component {
  IntSupplier supplier;
  int defaultValue;

  Integer(boolean isVariable, IntSupplier supplier, int defaultValue) {
    super(isVariable);
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  int get() {
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
          float advance(float seconds) {
            return 0;
          }
        });
      }
    };
  }

  public <R> Value<R> map(IntFunction<R> function) {
    return value(() -> function.apply(get()));
  }

  public Integer mapToInteger(IntUnaryOperator function) {
    return Integer.integer(() -> function.applyAsInt(get()));
  }

  public Number mapToNumber(IntToDoubleFunction function) {
    return Number.number(() -> function.applyAsDouble(get()));
  }

  public Bool mapToBool(IntPredicate function) {
    return bool(() -> function.test(get()));
  }

  public Integer plus(int x) {
    return integer(() -> get() + x);
  }

  public Integer plus(Integer n) {
    return integer(() -> get() + n.get());
  }

  public Integer times(int x) {
    return integer(() -> get() * x);
  }

  public Integer times(Integer n) {
    return integer(() -> get() * n.get());
  }

  public <T> Value<T> select(T... values) {
    return value(() -> values[Math.floorMod(get(), values.length)]);
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
