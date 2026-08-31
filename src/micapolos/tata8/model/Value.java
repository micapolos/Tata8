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

  public static <T> Value<T> value() {
    return new Value<>(false, null, null);
  }

  public static <T> Value<T> value(T value) {
    return new Value<>(false, null, value);
  }

  public static <T> Value<T> value(Supplier<T> aSupplier) {
    return new Value<>(false, aSupplier, null);
  }

  public static <T> Value<T> variable() {
    return variable(null);
  }

  public static <T> Value<T> variable(T value) {
    return variable(() -> value);
  }

  public static <T> Value<T> variable(Supplier<T> aSupplier) {
    var value = new Value<>(true, aSupplier, null);
    Game.addInit(() -> value.initialize.execute());
    return value;
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
