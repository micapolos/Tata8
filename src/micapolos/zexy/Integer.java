package micapolos.zexy;

import micapolos.IntegerUtils;
import micapolos.tata8.Random;

import java.util.List;
import java.util.function.*;

import static micapolos.Leo.*;
import static micapolos.zexy.Animation.*;
import static micapolos.zexy.Number.*;

public class Integer extends ValueComponent {
  IntSupplier supplier;
  int defaultValue;

  Integer(IntSupplier supplier) {
    this(noAnimation, supplier, 0);
  }

  Integer(Animation animation, IntSupplier supplier, int defaultValue) {
    this.animation = animation;
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  int get() {
    IntSupplier supplier = this.supplier;
    return supplier != null ? supplier.getAsInt() : defaultValue;
  }

  public static Integer randomIntegerBetween(int min, int max) {
    return integer(() -> Random.between(min, max));
  }

  public static Integer randomIntegerUntil(int limit) {
    return integer(() -> Random.until(limit));
  }

  public static Integer integerZero = integer(0);
  public static Integer integerOne = integer(1);

  public static Integer integer(int value) {
    return integer(() -> value);
  }

  static Integer integer(IntSupplier supplier) {
    return new Integer(noAnimation, supplier, 0);
  }

  public static Integer integer(Integer integer) {
    return new Integer(noAnimation, integer::get, 0) {
      @Override
      void addRunners() {
        integer.addRunnersOnce();
      }
    };
  }

  public static Integer newInteger() {
    return new Integer(null, null, 0);
  }

  public Integer update(IntUnaryOperator operator) {
    return new Integer(() -> operator.applyAsInt(get())) {
      @Override
      void addRunners() {
        Integer.this.addRunnersOnce();
      }
    };
  }

  public Integer update(Integer integer, IntBinaryOperator operator) {
    return new Integer(() -> operator.applyAsInt(get(), integer.get())) {
      @Override
      void addRunners() {
        Integer.this.addRunnersOnce();
        integer.addRunnersOnce();
      }
    };
  }

  public Number mapToNumber(IntToDoubleFunction function) {
    return new Number(() -> function.applyAsDouble(get())) {
      @Override
      void addRunners() {
        Integer.this.addRunnersOnce();
      }
    };
  }

  public Boolean mapToBool(IntPredicate function) {
    return new Boolean(() -> function.test(get())) {
      @Override
      void addRunners() {
        Integer.this.addRunnersOnce();
      }
    };
  }

  public <R> Value<R> mapToValue(IntFunction<R> function) {
    return new Value<>(() -> function.apply(get())) {
      @Override
      void addRunners() {
        Integer.this.addRunnersOnce();
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

  public Integer floorMod(int i) {
    return floorMod(integer(i));
  }

  public Integer floorMod(Integer integer) {
    return update(i -> Math.floorMod(Integer.this.get(), integer.get()));
  }

  public Number number() {
    return mapToNumber(IntegerUtils::toDouble);
  }

  public <T> Value<T> selectFrom(T... values) {
    return mapToValue(i -> values[Math.floorMod(i, values.length)]);
  }

  public <T> Value<T> selectFrom(micapolos.zexy.List<Value<T>> values) {
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
      void addRunners() {
        Integer.this.addRunnersOnce();
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
      void addRunners() {
        Integer.this.addRunnersOnce();
        integer.addRunnersOnce();
      }
    };
  }

  public Integer logged() {
    return update(n -> {
      micapolos.tata8.Game.log(Integer.this);
      return n;
    });
  }

  public Integer loggedWith(String label) {
    return update(n -> {
      micapolos.tata8.Game.log(leo(label, Integer.this));
      return n;
    });
  }

  public Event changeTo(int i) {
    return changeTo(integer(i));
  }

  public Event changeTo(Integer integer) {
    return change().and(isEqualTo(integer));
  }

  public final Event change() {
    return new Event() {
      int previous;

      @Override
      void addRunners() {
        Integer.this.addRunnersOnce();

        Game.add(new Runner() {
          @Override
          public void init() {
            previous = Integer.this.get();
            defaultOccurs = false;
          }

          @Override
          public void update(float seconds) {
            int current = Integer.this.get();
            defaultOccurs = current != previous;
            previous = current;
          }
        });
      }
    };
  }

  public final Boolean isEqualTo(int i) {
    return isEqualTo(integer(i));
  }

  public final Boolean isEqualTo(Integer integer) {
    return new Boolean(() -> get() == integer.get()) {
      @Override
      void addRunners() {
        Integer.this.addRunnersOnce();
        integer.addRunnersOnce();
      }
    };
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    var seconds = numberOfSeconds.toInteger();
    seconds.change().and(seconds.isEqualTo(2)).show();
  }
}
