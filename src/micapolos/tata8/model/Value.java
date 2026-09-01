package micapolos.tata8.model;

import micapolos.tata8.Random;

import java.util.function.*;

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

  public static <T> Value<T> withNull() {
    return new Value<>(false, null, null);
  }

  public static <T> Value<T> with(T value) {
    return new Value<>(false, null, value);
  }

  public static <T> Value<T> with(Supplier<T> aSupplier) {
    return new Value<>(false, aSupplier, null);
  }

  public static <T> Value<T> newVariable() {
    return newVariable(null);
  }

  public static <T> Value<T> newVariable(T value) {
    return newVariable(() -> value);
  }

  public static <T> Value<T> newVariable(Supplier<T> aSupplier) {
    var value = new Value<>(true, aSupplier, null);
    Game.addInit(() -> value.initialize.execute());
    return value;
  }

  public Value<T> toValue() {
    return isVariable ? with(this::get) : this;
  }

  public Value<T> update(UnaryOperator<T> operator) {
    return with(() -> operator.apply(get()));
  }

  public Value<T> update(Value<T> value, BinaryOperator<T> operator) {
    return with(() -> operator.apply(get(), value.get()));
  }

  public <R> Value<R> map(Function<T, R> function) {
    return with(() -> function.apply(get()));
  }

  public <V, R> Value<R> map(Value<V> x, BiFunction<T, V, R> function) {
    return with(() -> function.apply(get(), x.get()));
  }

  public Value<T> mapToNotNull(T defaultValue) {
    return map(value -> value != null ? value : defaultValue);
  }

  public Number mapToNumber(ToDoubleFunction<T> function) {
    return Number.with(() -> function.applyAsDouble(get()));
  }

  public Integer mapToInteger(ToIntFunction<T> function) {
    return Integer.with(() -> function.applyAsInt(get()));
  }

  public Boolean mapToBool(Predicate<T> function) {
    return Boolean.with(() -> function.test(get()));
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
