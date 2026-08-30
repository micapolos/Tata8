package micapolos.tata8.model;

public final class Property<T> extends Value<T> {
  public Value<T> value;

  Property(Value<T> initial) {
    this.value = initial;
  }

  public static <T> Property<T> property(Value<T> initial) {
    return new Property<>(initial);
  }

  @Override
  T get() {
    return value.get();
  }
}
