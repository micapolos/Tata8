package micapolos.tata8.model;

import micapolos.tata8.Random;

import java.util.function.Supplier;

public class Value<T> implements Showable {
  Supplier<T> supplier;
  T defaultValue;

  Value(Supplier<T> supplier, T defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  T get() {
    var supplier = this.supplier;
    return supplier != null ? supplier.get() : defaultValue;
  }

  static <T> Value<T> value() {
    return new Value<>(null, null) {
      {
        Game.add(new Clip() {
          @Override
          void start() {
            init(null, null);
          }

          @Override
          float advance(float seconds) {
            return 0;
          }
        });
      }
    };
  }

  static <T> Value<T> value(T value) {
    return new Value<>(null, value) {
      {
        Game.add(new Clip() {
          @Override
          void start() {
            init(null, value);
          }

          @Override
          float advance(float seconds) {
            return 0;
          }
        });
      }
    };
  }

  static <T> Value<T> value(Supplier<T> aSupplier) {
    return new Value<>(aSupplier, null) {
      {
        Game.add(new Clip() {
          @Override
          void start() {
            init(aSupplier, null);
          }

          @Override
          float advance(float seconds) {
            return 0;
          }
        });
      }
    };
  }

  void init(T value) {
    init(null, value);
  }

  void init(Value<T> value) {
    init(value::get, null);
  }

  void init(Supplier<T> supplier, T defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public Action set(T value) {
    return () -> init(value);
  }

  public Action set(Value<T> value) {
    return () -> init(value);
  }

  public Action capture(Value<T> value) {
    return () -> init(value.get());
  }

  public static <T> Value<T> randomFrom(T... values) {
    return value(() -> values[Random.until(values.length)]);
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    long now = System.currentTimeMillis();
    Value.value(() -> (int) (System.currentTimeMillis() - now)).show();
  }
}
