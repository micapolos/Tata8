package micapolos.tata8.model;

import java.util.function.Supplier;

public abstract class Value<T> extends Component {
  abstract T get();

  static <T> Value<T> with(T t) {
    return new Value<T>() {
      @Override
      T get() {
        return t;
      }
    };
  }

  public static <T> Value<T> with(Supplier<T> supplier) {
    return new Value<T>() {
      @Override
      T get() {
        return supplier.get();
      }
    };
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    with("Hello").show();
  }
}
