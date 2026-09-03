package micapolos.zexy;

import micapolos.tata8.Random;

import java.util.function.*;

import static micapolos.zexy.Animation.*;

public class Value<T> extends ValueComponent {
  Supplier<T> currentSupplier;
  T currentValue;

  Value(T value) {
    this(null, value);
  }

  Value(Supplier<T> supplier) {
    this(supplier, null);
  }

  Value(Animation animation, Supplier<T> supplier) {
    this(animation, supplier, null);
  }

  Value(Supplier<T> supplier, T defaultValue) {
    this(noAnimation, supplier, defaultValue);
  }

  Value(Animation animation, Supplier<T> supplier, T defaultValue) {
    this.animation = animation;
    this.currentSupplier = supplier;
    this.currentValue = defaultValue;
  }

  public Value<T> with(Animation animation) {
    return new Value<>(animation, this::get);
  }

  public T get() {
    var supplier = this.currentSupplier;
    return supplier != null ? supplier.get() : currentValue;
  }

  public static <T> Value<T> nullValue() {
    return value((T) null);
  }

  public static <T> Value<T> value(T value) {
    return new Value<>(value);
  }

  public static <T> Value<T> value(Value<T> value) {
    return new Value<>(value::get) {
      @Override
      void addRunners() {
        value.addRunnersOnce();
      }
    };
  }

  static <T> Value<T> value(Supplier<T> supplier) {
    return new Value<>(supplier, null);
  }

  public static <T> Value<T> newVariable() {
    return new Value<>(null, null, null);
  }

  public Value<T> readonly() {
    return isReadonly() ? this : value(this);
  }

  public Value<T> update(UnaryOperator<T> operator) {
    return new Value<>(() -> operator.apply(get())) {
      @Override
      void addRunners() {
        Value.this.addRunnersOnce();
      }
    };
  }

  public Value<T> update(Value<T> value, BinaryOperator<T> operator) {
    return new Value<>(() -> operator.apply(get(), value.get())) {
      @Override
      void addRunners() {
        Value.this.addRunnersOnce();
        value.addRunnersOnce();
      }
    };
  }

  public Action mapToAction(Runnable runnable) {
    return new Action() {
      @Override
      void execute() {
        runnable.run();
      }

      @Override
      void addRunners() {
        Value.this.addRunnersOnce();
      }
    };
  }

  public Action mapToAction(Component component, Runnable runnable) {
    return new Action() {
      @Override
      void execute() {
        runnable.run();
      }

      @Override
      void addRunners() {
        Value.this.addRunnersOnce();
        component.addRunnersOnce();
      }
    };
  }

  public <R> Value<R> map(Function<T, R> function) {
    return new Value<>(() -> function.apply(get())) {
      @Override
      void addRunners() {
        Value.this.addRunnersOnce();
      }
    };
  }

  public <V, R> Value<R> map(Value<V> value, BiFunction<T, V, R> function) {
    return new Value<>(() -> function.apply(get(), value.get())) {
      @Override
      void addRunners() {
        Value.this.addRunnersOnce();
        value.addRunnersOnce();
      }
    };
  }

  public Value<T> orIfNull(T defaultValue) {
    return map(value -> value != null ? value : defaultValue);
  }

  public Number mapToNumber(ToDoubleFunction<T> function) {
    return new Number(() -> function.applyAsDouble(get())) {
      @Override
      void addRunners() {
        Value.this.addRunnersOnce();
      }
    };
  }

  public Integer mapToInteger(ToIntFunction<T> function) {
    return new Integer(() -> function.applyAsInt(get())) {
      @Override
      void addRunners() {
        Value.this.addRunnersOnce();
      }
    };
  }

  public Boolean mapToBool(Predicate<T> function) {
    return new Boolean(() -> function.test(get())) {
      @Override
      void addRunners() {
        Value.this.addRunnersOnce();
      }
    };
  }

  void setImmediately(T value) {
    setImmediately(null, value);
  }

  void setImmediately(Value<T> value) {
    setImmediately(value::get, null);
  }

  void setImmediately(Supplier<T> supplier, T defaultValue) {
    this.currentSupplier = supplier;
    this.currentValue = defaultValue;
  }

  public Action set(T value) {
    checkVariable();
    return mapToAction(() -> setImmediately(value));
  }

  public Action set(Value<T> value) {
    checkVariable();
    return mapToAction(value, () -> setImmediately(value));
  }

  public Action capture(Value<T> value) {
    checkVariable();
    return mapToAction(value, () -> setImmediately(value.get()));
  }

  public static <T> Value<T> randomFrom(T... values) {
    return new Value<>(() -> values[Random.until(values.length)]);
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {

  }
}
