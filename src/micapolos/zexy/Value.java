package micapolos.zexy;

import micapolos.tata8.Random;

import java.util.function.*;

public class Value<T> extends ValueComponent {
  Supplier<T> supplier;
  T defaultValue;

  Value(T value) {
    this(null, value);
  }

  Value(Supplier<T> supplier) {
    this(supplier, null);
  }

  Value(Clip clip, Supplier<T> supplier) {
    this(clip, false, supplier, null);
  }

  Value(Supplier<T> supplier, T defaultValue) {
    this(false, supplier, defaultValue);
  }

  Value(boolean isVariable, Supplier<T> supplier, T defaultValue) {
    this(Clip.emptyClip, isVariable, supplier, defaultValue);
  }

  Value(Clip clip, boolean isVariable, Supplier<T> supplier, T defaultValue) {
    super(clip, isVariable);
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public Value<T> with(Clip clip) {
    return new Value<>(clip, this::get);
  }

  public T get() {
    var supplier = this.supplier;
    return supplier != null ? supplier.get() : defaultValue;
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
    return newVariable((T) null);
  }

  public static <T> Value<T> newVariable(T value) {
    return new Value<>(true, null, value) {
      @Override
      void addRunners() {
        Game.add(new Clip() {
          @Override
          void start() {
            supplier = null;
            defaultValue = value;
          }

          @Override
          float step(float seconds) {
            return seconds;
          }
        });
      }
    };
  }

  public static <T> Value<T> newVariable(Value<T> value) {
    return new Value<>(true, value::get, null) {
      @Override
      void addRunners() {
        Game.add(new Clip() {
          @Override
          void start() {
            supplier = value::get;
            defaultValue = null;
          }

          @Override
          float step(float seconds) {
            return seconds;
          }
        });
      }
    };
  }

  public Value<T> readonly() {
    return isVariable ? value(this) : this;
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
    this.supplier = supplier;
    this.defaultValue = defaultValue;
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
