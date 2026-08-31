package micapolos.tata8.model;

import java.util.function.BooleanSupplier;

import static micapolos.tata8.model.Game.add;
import static micapolos.tata8.model.Number.number;
import static micapolos.tata8.model.Value.value;

public class Bool extends Component {
  BooleanSupplier supplier;
  boolean defaultValue;

  Bool(boolean isVariable, BooleanSupplier supplier, boolean defaultValue) {
    super(isVariable);
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public boolean get() {
    BooleanSupplier supplier = this.supplier;
    return supplier != null ? supplier.getAsBoolean() : defaultValue;
  }

  public static Bool bool(boolean value) {
    return new Bool(false, null, value);
  }

  static Bool bool(BooleanSupplier aSupplier) {
    return new Bool(false, aSupplier, false);
  }

  public static Bool variable() {
    return variable(false);
  }

  public static Bool variable(boolean value) {
    return variable(() -> value);
  }

  static Bool variable(BooleanSupplier supplier) {
    var bool = new Bool(true, supplier, false);
    Game.addInit(() -> bool.setImmediately(supplier, false));
    return bool;
  }

  void setImmediately(boolean x) {
    setImmediately(null, x);
  }

  void setImmediately(Bool number) {
    setImmediately(number::get, false);
  }

  void setImmediately(BooleanSupplier supplier, boolean defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public void init(boolean b) {
    init(() -> setImmediately(b));
  }

  public void init(Bool b) {
    init(() -> setImmediately(b));
  }

  public Action set(boolean x) {
    checkVariable();
    return () -> setImmediately(x);
  }

  public Action set(Bool x) {
    checkVariable();
    return () -> setImmediately(x);
  }

  public Action negate() {
    checkVariable();
    return () -> setImmediately(!get());
  }

  public Event changed() {
    return new Event() {
      boolean previousValue = get();

      {
        add(new Clip() {
          @Override
          void start() {

          }

          @Override
          float advance(float seconds) {
            previousValue = get();
            return 0;
          }
        });
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

  public static Bool all(Bool... bools) {
    return bool(() -> {
      boolean value = true;
      for (Bool bool : bools) {
        value &= bool.get();
      }
      return value;
    });
  }

  public static Bool any(Bool... bools) {
    return bool(() -> {
      boolean value = false;
      for (Bool bool : bools) {
        value |= bool.get();
      }
      return value;
    });
  }

  public Bool and(Bool bool) {
    return bool(() -> get() && bool.get());
  }

  public Bool or(Bool bool) {
    return bool(() -> get() || bool.get());
  }

  public Event changedTo(boolean value) {
    return changedTo(bool(value));
  }

  public Event changedTo(Bool bool) {
    return changed().and(equals(bool));
  }

  public <T> Value<T> select(T trueValue, T falseValue) {
    return value(() -> get() ? trueValue : falseValue);
  }

  public <T> Value<T> select(Value<T> trueValue, Value<T> falseValue) {
    return value(() -> get() ? trueValue.get() : falseValue.get());
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
