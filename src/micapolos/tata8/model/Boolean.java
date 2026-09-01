package micapolos.tata8.model;

import java.util.function.BooleanSupplier;

import static micapolos.tata8.model.Number.number;
import static micapolos.tata8.model.Value.with;

public class Boolean extends Component {
  BooleanSupplier supplier;
  boolean defaultValue;

  Boolean(BooleanSupplier supplier) {
    this(false, supplier, false);
  }

  Boolean(boolean isVariable, BooleanSupplier supplier, boolean defaultValue) {
    super(isVariable);
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public boolean get() {
    BooleanSupplier supplier = this.supplier;
    return supplier != null ? supplier.getAsBoolean() : defaultValue;
  }

  public static Boolean with(boolean b) {
    return new Boolean(false, null, b);
  }

  static Boolean with(BooleanSupplier aSupplier) {
    return new Boolean(false, aSupplier, false);
  }

  public static Boolean newVariable() {
    return newVariable(false);
  }

  public static Boolean newVariable(boolean value) {
    return newVariable(() -> value);
  }

  static Boolean newVariable(BooleanSupplier supplier) {
    var bool = new Boolean(true, supplier, false);
    Game.addInit(() -> bool.setImmediately(supplier, false));
    return bool;
  }

  void setImmediately(boolean x) {
    setImmediately(null, x);
  }

  void setImmediately(Boolean number) {
    setImmediately(number::get, false);
  }

  void setImmediately(BooleanSupplier supplier, boolean defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public void init(boolean b) {
    init(() -> setImmediately(b));
  }

  public void init(Boolean b) {
    init(() -> setImmediately(b));
  }

  public Action set(boolean x) {
    checkVariable();
    return () -> setImmediately(x);
  }

  public Action set(Boolean x) {
    checkVariable();
    return () -> setImmediately(x);
  }

  public Action negate() {
    checkVariable();
    return () -> setImmediately(!get());
  }

  public Boolean equals(boolean value) {
    return equals(with(value));
  }

  public Boolean equals(Boolean value) {
    return with(() -> get() == value.get());
  }

  public static Boolean not(Boolean value) {
    return with(() -> !value.get());
  }

  public static Boolean all(Boolean... aBooleans) {
    return with(() -> {
      boolean value = true;
      for (Boolean aBoolean : aBooleans) {
        value &= aBoolean.get();
      }
      return value;
    });
  }

  public static Boolean any(Boolean... aBooleans) {
    return with(() -> {
      boolean value = false;
      for (Boolean aBoolean : aBooleans) {
        value |= aBoolean.get();
      }
      return value;
    });
  }

  public Boolean and(Boolean aBoolean) {
    return with(() -> get() && aBoolean.get());
  }

  public Boolean or(Boolean aBoolean) {
    return with(() -> get() || aBoolean.get());
  }

  public <T> Value<T> select(T trueValue, T falseValue) {
    return Value.with(() -> get() ? trueValue : falseValue);
  }

  public <T> Value<T> select(Value<T> trueValue, Value<T> falseValue) {
    return Value.with(() -> get() ? trueValue.get() : falseValue.get());
  }

  public Number select(Number trueNumber, Number falseNumber) {
    return Number.number(() -> get() ? trueNumber.get() : falseNumber.get());
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    with(false).show();
  }
}
