package micapolos.tata8.model;

import micapolos.tata8.Random;

import java.util.function.Supplier;

public class Value<T> extends Component {
  Supplier<T> supplier;
  T defaultValue;

  Value(boolean isVariable, Supplier<T> supplier, T defaultValue) {
    super(isVariable);
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  T get() {
    var supplier = this.supplier;
    return supplier != null ? supplier.get() : defaultValue;
  }

  static <T> Value<T> value() {
    return new Value<>(false, null, null);
  }

  static <T> Value<T> value(T value) {
    return new Value<>(false, null, value);
  }

  static <T> Value<T> value(Supplier<T> aSupplier) {
    return new Value<>(false, aSupplier, null);
  }

  static <T> Value<T> variable() {
    return variable(null);
  }

  static <T> Value<T> variable(T value) {
    return variable(() -> value);
  }

  static <T> Value<T> variable(Supplier<T> aSupplier) {
    return new Value<>(true,  aSupplier, null) {
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
    checkVariable();
    return () -> init(value);
  }

  public Action set(Value<T> value) {
    checkVariable();
    return () -> init(value);
  }

  public Action capture(Value<T> value) {
    checkVariable();
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
