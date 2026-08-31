package micapolos.tata8.model;

import micapolos.tata8.Random;

import java.util.function.IntSupplier;

import static micapolos.tata8.model.Value.value;

public class Integer implements Showable {
  IntSupplier supplier;
  int defaultValue;

  Integer(IntSupplier supplier, int defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  int get() {
    IntSupplier supplier = this.supplier;
    return supplier != null ? supplier.getAsInt() : defaultValue;
  }

  public static Integer randomIndex(int limit) {
    return integer(() -> Random.until(limit));
  }

  public static Integer integer() {
    return integer(0);
  }

  public static Integer integer(int value) {
    return new Integer(null, value) {
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

  public static Integer integer(IntSupplier aSupplier) {
    return new Integer(aSupplier, 0) {
      {
        Game.add(new Clip() {
          @Override
          void start() {
            init(aSupplier, 0);
          }

          @Override
          float advance(float seconds) {
            return 0;
          }
        });
      }
    };
  }

  public Integer plus(int x) {
    return integer(() -> get() + x);
  }

  public Integer plus(Integer n) {
    return integer(() -> get() + n.get());
  }

  public Integer times(int x) {
    return integer(() -> get() * x);
  }

  public Integer times(Integer n) {
    return integer(() -> get() * n.get());
  }

  public <T> Value<T> select(T... values) {
    return value(() -> values[Math.floorMod(get(), values.length)]);
  }

  void init(int x) {
    init(null, x);
  }

  void init(Integer number) {
    init(number::get, 0);
  }

  void init(IntSupplier supplier, int defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public Action set(int x) {
    return () -> init(x);
  }

  public Action set(Integer number) {
    return () -> init(number);
  }

  public Action capture(Integer number) {
    return () -> init(number.get());
  }

  public Action add(int d) {
    return () -> init(get() + d);
  }

  public Action add(Integer n) {
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
