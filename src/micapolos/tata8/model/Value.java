package micapolos.tata8.model;

import micapolos.tata8.Random;

import java.util.function.*;

import static micapolos.tata8.model.Bool.bool;
import static micapolos.tata8.model.Integer.integer;
import static micapolos.tata8.model.Number.number;

public class Value<T> extends Component {
  Supplier<T> supplier;
  T defaultValue;

  Value(boolean isVariable, Supplier<T> supplier, T defaultValue) {
    super(isVariable);
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public T get() {
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

  public Value<T> toValue() {
    return isVariable ? value(this::get) : this;
  }

  public Value<T> update(UnaryOperator<T> operator) {
    return value(() -> operator.apply(get()));
  }

  public Value<T> update(Value<T> value, BinaryOperator<T> operator) {
    return value(() -> operator.apply(get(), value.get()));
  }

  public <R> Value<R> map(Function<T, R> function) {
    return value(() -> function.apply(get()));
  }

  public <V, R> Value<R> map(Value<V> x, BiFunction<T, V, R> function) {
    return value(() -> function.apply(get(), x.get()));
  }

  public Value<T> mapToNotNull(T defaultValue) {
    return map(value -> value != null ? value : defaultValue);
  }

  public Number mapToNumber(ToDoubleFunction<T> function) {
    return number(() -> function.applyAsDouble(get()));
  }

  public Integer mapToInteger(ToIntFunction<T> function) {
    return integer(() -> function.applyAsInt(get()));
  }

  public Bool mapToBool(Predicate<T> function) {
    return bool(() -> function.test(get()));
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
    var images = Image.image(Value.class, "depressedChicken.png").sliceVertically(8);
    value(images[0]).show();
  }
}
