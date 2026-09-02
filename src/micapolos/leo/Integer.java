package micapolos.leo;

import micapolos.IntegerUtils;
import micapolos.tata8.Random;

import java.util.List;
import java.util.function.*;

public class Integer extends ValueComponent {
  IntSupplier supplier;
  int defaultValue;

  Integer(IntSupplier supplier) {
    this(false, supplier, 0);
  }

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

  public static Integer newInteger() {
    return newInteger(0);
  }

  public static Integer newInteger(int value) {
    return newInteger(() -> value);
  }

  public static Integer newInteger(IntSupplier aSupplier) {
    return new Integer(true, aSupplier, 0) {
      {
        Game.add(new Clip() {
          @Override
          void start() {
            setImmediately(aSupplier, 0);
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
    return new Integer(() -> operator.applyAsInt(get())) {
      @Override
      void addClips() {
        Integer.this.maybeAddClips();
      }
    };
  }

  public Integer update(Integer integer, IntBinaryOperator operator) {
    return new Integer(() -> operator.applyAsInt(get(), integer.get())) {
      @Override
      void addClips() {
        Integer.this.maybeAddClips();
        integer.maybeAddClips();
      }
    };
  }

  public Number mapToNumber(IntToDoubleFunction function) {
    return new Number(() -> function.applyAsDouble(get())) {
      @Override
      void addClips() {
        Integer.this.maybeAddClips();
      }
    };
  }

  public Boolean mapToBool(IntPredicate function) {
    return new Boolean(() -> function.test(get())) {
      @Override
      void addClips() {
        Integer.this.maybeAddClips();
      }
    };
  }

  public <R> Value<R> mapToValue(IntFunction<R> function) {
    return new Value<>(() -> function.apply(get())) {
      @Override
      void addClips() {
        Integer.this.maybeAddClips();
      }
    };
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

  public <T> Value<T> selectFrom(T... values) {
    return mapToValue(i -> values[Math.floorMod(i, values.length)]);
  }

  public <T> Value<T> selectFrom(micapolos.leo.List<Value<T>> values) {
    return values.get(this);
  }

  public <T> Value<T> get(List<T> values) {
    return mapToValue(i -> values.get(Math.floorMod(i, values.size())));
  }

  void setImmediately(int x) {
    setImmediately(null, x);
  }

  void setImmediately(Integer number) {
    setImmediately(number::get, 0);
  }

  void setImmediately(IntSupplier supplier, int defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public Action set(int x) {
    checkVariable();
    return new Action() {
      @Override
      void execute() {
        Integer.this.setImmediately(x);
      }

      @Override
      void addClips() {
        Integer.this.maybeAddClips();
      }
    };
  }

  public Action add(int i) {
    return add(integer(i));
  }

  public Action add(Integer integer) {
    return new Action() {
      @Override
      void execute() {
        setImmediately(get() + integer.get());
      }

      @Override
      void addClips() {
        Integer.this.maybeAddClips();
        integer.maybeAddClips();
      }
    };
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    randomUntil(10).show();
  }
}
