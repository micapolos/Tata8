package micapolos.zexy;

import java.util.function.BooleanSupplier;

public class Boolean extends ValueComponent {
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

  public static Boolean bool(boolean b) {
    return new Boolean(false, null, b);
  }

  static Boolean bool(BooleanSupplier aSupplier) {
    return new Boolean(false, aSupplier, false);
  }

  public static Boolean newBoolean() {
    return newBoolean(false);
  }

  public static Boolean newBoolean(boolean value) {
    return newBoolean(() -> value);
  }

  static Boolean newBoolean(BooleanSupplier supplier) {
    return new Boolean(true, supplier, false);
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

  public Action set(boolean x) {
    return set(Boolean.bool(x));
  }

  public Action set(Boolean x) {
    checkVariable();
    return new Action() {
      @Override
      void execute() {
        setImmediately(x);
      }

      @Override
      void addRunners() {
        Boolean.this.addRunnersOnce();
      }
    };
  }

  public Action negate() {
    checkVariable();
    return new Action() {
      @Override
      void execute() {
        setImmediately(!get());
      }

      @Override
      void addRunners() {
        Boolean.this.addRunnersOnce();
      }
    };
  }

  public Boolean equals(boolean value) {
    return equals(bool(value));
  }

  public Boolean equals(Boolean value) {
    return new Boolean(() -> get() == value.get()) {
      @Override
      void addRunners() {
        Boolean.this.addRunnersOnce();
        value.addRunnersOnce();
      }
    };
  }

  public static Boolean not(Boolean value) {
    return new Boolean(() -> !value.get()) {
      @Override
      void addRunners() {
        value.addRunnersOnce();
      }
    };
  }

  public static Boolean all(Boolean... aBooleans) {
    return new Boolean(() -> {
      boolean value = true;
      for (Boolean aBoolean : aBooleans) {
        value &= aBoolean.get();
      }
      return value;
    }) {
      @Override
      void addRunners() {
        for (Boolean bool : aBooleans) {
          bool.addRunnersOnce();
        }
      }
    };
  }

  public static Boolean any(Boolean... aBooleans) {
    return new Boolean(() -> {
      boolean value = false;
      for (Boolean aBoolean : aBooleans) {
        value |= aBoolean.get();
      }
      return value;
    }) {
      @Override
      void addRunners() {
        for (Boolean bool : aBooleans) {
          bool.addRunnersOnce();
        }
      }
    };
  }

  public Boolean and(Boolean aBoolean) {
    return new Boolean(() -> get() && aBoolean.get()) {
      @Override
      void addRunners() {
        Boolean.this.addRunnersOnce();
        aBoolean.addRunners();
      }
    };
  }

  public Boolean or(Boolean aBoolean) {
    return new Boolean(() -> get() || aBoolean.get()) {
      @Override
      void addRunners() {
        Boolean.this.addRunnersOnce();
        aBoolean.addRunners();
      }
    };
  }

  public <T> Value<T> selectValue(T trueValue, T falseValue) {
    return new Value<>(() -> get() ? trueValue : falseValue) {
      @Override
      void addRunners() {
        Boolean.this.addRunners();
      }
    };
  }

  public Number select(Number trueNumber, Number falseNumber) {
    return new Number(() -> get() ? trueNumber.get() : falseNumber.get()) {
      @Override
      void addRunners() {
        Boolean.this.addRunners();
        trueNumber.addRunnersOnce();
        falseNumber.addRunnersOnce();
      }
    };
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    bool(false).show();
  }
}
