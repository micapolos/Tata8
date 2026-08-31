package micapolos.tata8.model;

import micapolos.tata8.Random;

import java.util.function.IntSupplier;

import static micapolos.tata8.model.Value.value;

public class Index extends Component {
  IntSupplier supplier;
  int defaultValue;

  Index(IntSupplier supplier, int defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  int get() {
    IntSupplier supplier = this.supplier;
    return supplier != null ? supplier.getAsInt() : defaultValue;
  }

  public static Index randomIndex(int limit) {
    return index(() -> Random.until(limit));
  }

  public static Index index() {
    return index(0);
  }

  public static Index index(int value) {
    return new Index(null, value) {
      @Override
      void start() {
        init(null, value);
      }
    };
  }

  public static Index index(IntSupplier aSupplier) {
    return new Index(aSupplier, 0) {
      @Override
      void start() {
        init(aSupplier, 0);
      }
    };
  }

  public Index plus(int x) {
    return index(() -> get() + x);
  }

  public Index plus(Index n) {
    return index(() -> get() + n.get());
  }

  public Index times(int x) {
    return index(() -> get() * x);
  }

  public Index times(Index n) {
    return index(() -> get() * n.get());
  }

  public <T> Value<T> select(T... values) {
    return value(() -> values[Math.floorMod(get(), values.length)]);
  }

  void init(int x) {
    init(null, x);
  }

  void init(Index number) {
    init(number::get, 0);
  }

  void init(IntSupplier supplier, int defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public Action set(int x) {
    return () -> init(x);
  }

  public Action set(Index number) {
    return () -> init(number);
  }

  public Action capture(Index number) {
    return () -> init(number.get());
  }

  public Action add(int d) {
    return () -> init(get() + d);
  }

  public Action add(Index n) {
    return () -> init(get() + n.get());
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    randomIndex(10).show();
  }
}
