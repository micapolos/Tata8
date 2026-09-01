package micapolos.tata8.model;

import java.util.function.BooleanSupplier;

import static micapolos.tata8.model.DoubleValue.with;
import static micapolos.tata8.model.Value.with;

public class BooleanValue extends Component {
  BooleanSupplier supplier;
  boolean defaultValue;

  BooleanValue(boolean isVariable, BooleanSupplier supplier, boolean defaultValue) {
    super(isVariable);
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public boolean get() {
    BooleanSupplier supplier = this.supplier;
    return supplier != null ? supplier.getAsBoolean() : defaultValue;
  }

  public static BooleanValue with(boolean b) {
    return new BooleanValue(false, null, b);
  }

  static BooleanValue with(BooleanSupplier aSupplier) {
    return new BooleanValue(false, aSupplier, false);
  }

  public static BooleanValue newVariable() {
    return newVariable(false);
  }

  public static BooleanValue newVariable(boolean value) {
    return newVariable(() -> value);
  }

  static BooleanValue newVariable(BooleanSupplier supplier) {
    var bool = new BooleanValue(true, supplier, false);
    Game.addInit(() -> bool.setImmediately(supplier, false));
    return bool;
  }

  void setImmediately(boolean x) {
    setImmediately(null, x);
  }

  void setImmediately(BooleanValue number) {
    setImmediately(number::get, false);
  }

  void setImmediately(BooleanSupplier supplier, boolean defaultValue) {
    this.supplier = supplier;
    this.defaultValue = defaultValue;
  }

  public void init(boolean b) {
    init(() -> setImmediately(b));
  }

  public void init(BooleanValue b) {
    init(() -> setImmediately(b));
  }

  public Action set(boolean x) {
    checkVariable();
    return () -> setImmediately(x);
  }

  public Action set(BooleanValue x) {
    checkVariable();
    return () -> setImmediately(x);
  }

  public Action negate() {
    checkVariable();
    return () -> setImmediately(!get());
  }

  public BooleanValue equals(boolean value) {
    return equals(with(value));
  }

  public BooleanValue equals(BooleanValue value) {
    return with(() -> get() == value.get());
  }

  public static BooleanValue not(BooleanValue value) {
    return with(() -> !value.get());
  }

  public static BooleanValue all(BooleanValue... booleanValues) {
    return with(() -> {
      boolean value = true;
      for (BooleanValue booleanValue : booleanValues) {
        value &= booleanValue.get();
      }
      return value;
    });
  }

  public static BooleanValue any(BooleanValue... booleanValues) {
    return with(() -> {
      boolean value = false;
      for (BooleanValue booleanValue : booleanValues) {
        value |= booleanValue.get();
      }
      return value;
    });
  }

  public BooleanValue and(BooleanValue booleanValue) {
    return with(() -> get() && booleanValue.get());
  }

  public BooleanValue or(BooleanValue booleanValue) {
    return with(() -> get() || booleanValue.get());
  }

  public <T> Value<T> select(T trueValue, T falseValue) {
    return Value.with(() -> get() ? trueValue : falseValue);
  }

  public <T> Value<T> select(Value<T> trueValue, Value<T> falseValue) {
    return Value.with(() -> get() ? trueValue.get() : falseValue.get());
  }

  public DoubleValue select(DoubleValue trueDoubleValue, DoubleValue falseDoubleValue) {
    return DoubleValue.with(() -> get() ? trueDoubleValue.get() : falseDoubleValue.get());
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    with(false).show();
  }
}
