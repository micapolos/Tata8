package micapolos.tata8.model;

import micapolos.tata8.Random;

import java.util.function.*;

public class Value<T> extends Component {
  Supplier<T> supplier;
  T defaultValue;

  Value(T value) {
    this(null, value);
  }

  Value(Supplier<T> supplier) {
    this(supplier, null);
  }

  Value(Supplier<T> supplier, T defaultValue) {
    this(false, supplier, defaultValue);
  }

  Value(boolean isVariable, Supplier<T> supplier, T defaultValue) {
    super(isVariable);
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public T get() {
    var supplier = this.supplier;
    return supplier != null ? supplier.get() : defaultValue;
  }

  public static <T> Value<T> withNull() {
    return with((T) null);
  }

  public static <T> Value<T> with(T value) {
    return new Value<>(value);
  }

  public static <T> Value<T> with(Value<T> value) {
    return new Value<>(value::get) {
      @Override
      void addClips() {
        value.maybeAddClips();
      }
    };
  }

  static <T> Value<T> with(Supplier<T> supplier) {
    return new Value<>(supplier, null);
  }

  public static <T> Value<T> newVariable() {
    return newVariable((T) null);
  }

  public static <T> Value<T> newVariable(T value) {
    return new Value<>(true, null, value) {
      @Override
      void addClips() {
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
      void addClips() {
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

  public Value<T> toValue() {
    return isVariable ? with(this) : this;
  }

  public Value<T> update(UnaryOperator<T> operator) {
    return new Value<>(() -> operator.apply(get())) {
      @Override
      void addClips() {
        Value.this.maybeAddClips();
      }
    };
  }

  public Value<T> update(Value<T> value, BinaryOperator<T> operator) {
    return new Value<>(() -> operator.apply(get(), value.get())) {
      @Override
      void addClips() {
        Value.this.maybeAddClips();
        value.maybeAddClips();
      }
    };
  }

  public <R> Value<R> map(Function<T, R> function) {
    return new Value<>(() -> function.apply(get())) {
      @Override
      void addClips() {
        Value.this.maybeAddClips();
      }
    };
  }

  public <V, R> Value<R> map(Value<V> value, BiFunction<T, V, R> function) {
    return new Value<>(() -> function.apply(get(), value.get())) {
      @Override
      void addClips() {
        Value.this.maybeAddClips();
        value.maybeAddClips();
      }
    };
  }

  public Value<T> mapToNotNull(T defaultValue) {
    return map(value -> value != null ? value : defaultValue);
  }

  public Number mapToNumber(ToDoubleFunction<T> function) {
    return new Number(() -> function.applyAsDouble(get())) {
      @Override
      void addClips() {
        Value.this.maybeAddClips();
      }
    };
  }

  public Integer mapToInteger(ToIntFunction<T> function) {
    return new Integer(() -> function.applyAsInt(get())) {
      @Override
      void addClips() {
        Value.this.maybeAddClips();
      }
    };
  }

  public Boolean mapToBool(Predicate<T> function) {
    return new Boolean(() -> function.test(get())) {
      @Override
      void addClips() {
        Value.this.maybeAddClips();
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

  public void init(T value) {
    init(() -> setImmediately(value));
  }

  public void init(Value<T> value) {
    init(() -> setImmediately(value));
  }

  public Action set(T value) {
    checkVariable();
    return () -> setImmediately(value);
  }

  public Action set(Value<T> value) {
    checkVariable();
    return () -> setImmediately(value);
  }

  public Action capture(Value<T> value) {
    checkVariable();
    return () -> setImmediately(value.get());
  }

  public static <T> Value<T> randomFrom(T... values) {
    return with(() -> values[Random.until(values.length)]);
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    var images = Image.load(Value.class, "depressedChicken.png").sliceVertically(8);
    with(images[0]).show();
  }
}
