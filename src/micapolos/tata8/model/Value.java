package micapolos.tata8.model;

public abstract class Value<T> {
  abstract T get();

  static <T> Value<T> constant(T t) {
    return new Value<T>() {
      @Override
      T get() {
        return t;
      }
    };
  }
}
