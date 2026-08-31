package micapolos.tata8.model;

import java.util.function.BooleanSupplier;

import static micapolos.tata8.model.Game.add;
import static micapolos.tata8.model.Number.number;

public class Bool extends Component {
  BooleanSupplier supplier;
  boolean defaultValue;

  Bool(BooleanSupplier supplier, boolean defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  boolean get() {
    BooleanSupplier supplier = this.supplier;
    return supplier != null ? supplier.getAsBoolean() : defaultValue;
  }

  public static Bool bool() {
    return bool(false);
  }

  public static Bool bool(boolean value) {
    return new Bool(null, value) {
      @Override
      void start() {
        init(value);
      }
    };
  }

  static Bool bool(BooleanSupplier aSupplier) {
    return new Bool(aSupplier, false) {
      @Override
      void start() {
        init(aSupplier, false);
      }
    };
  }

  void init(boolean x) {
    init(null, x);
  }

  void init(Bool number) {
    init(number::get, false);
  }

  void init(BooleanSupplier supplier, boolean defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public Action set(boolean x) {
    return () -> init(x);
  }

  public Action set(Bool x) {
    return () -> init(x);
  }

  public Event changed() {
    return new Event() {
      boolean previousValue = get();

      {
        add(() -> previousValue = get());
      }

      @Override
      public boolean didHappen() {
        return previousValue != get();
      }
    };
  }

  public Bool equals(boolean value) {
    return equals(bool(value));
  }

  public Bool equals(Bool value) {
    return bool(() -> get() == value.get());
  }

  public static Bool not(Bool value) {
    return bool(() -> !value.get());
  }

  public Event changedTo(boolean value) {
    return changedTo(value);
  }

  public Event changedTo(Bool bool) {
    return changed().and(equals(bool));
  }

  public <T> Value<T> select(Value<T> trueValue, Value<T> falseValue) {
    return Value.value(() -> get() ? trueValue.get() : falseValue.get());
  }

  public Number select(Number trueNumber, Number falseNumber) {
    return number(() -> get() ? trueNumber.get() : falseNumber.get());
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    bool(false).show();
  }
}
