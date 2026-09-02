package micapolos.leo;

import java.util.function.BooleanSupplier;

import static micapolos.leo.Action.action;

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
    var bool = new Boolean(true, supplier, false);
    Game.addInit(action(() -> bool.setImmediately(supplier, false)));
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
      void addClips() {
        Boolean.this.maybeAddClips();
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
      void addClips() {
        Boolean.this.maybeAddClips();
      }
    };
  }

  public Boolean equals(boolean value) {
    return equals(bool(value));
  }

  public Boolean equals(Boolean value) {
    return new Boolean(() -> get() == value.get()) {
      @Override
      void addClips() {
        Boolean.this.maybeAddClips();
        value.maybeAddClips();
      }
    };
  }

  public static Boolean not(Boolean value) {
    return new Boolean(() -> !value.get()) {
      @Override
      void addClips() {
        value.maybeAddClips();
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
      void addClips() {
        for (Boolean bool : aBooleans) {
          bool.maybeAddClips();
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
      void addClips() {
        for (Boolean bool : aBooleans) {
          bool.maybeAddClips();
        }
      }
    };
  }

  public Boolean and(Boolean aBoolean) {
    return new Boolean(() -> get() && aBoolean.get()) {
      @Override
      void addClips() {
        Boolean.this.maybeAddClips();
        aBoolean.addClips();
      }
    };
  }

  public Boolean or(Boolean aBoolean) {
    return new Boolean(() -> get() || aBoolean.get()) {
      @Override
      void addClips() {
        Boolean.this.maybeAddClips();
        aBoolean.addClips();
      }
    };
  }

  public <T> Value<T> selectValue(T trueValue, T falseValue) {
    return new Value<>(() -> get() ? trueValue : falseValue) {
      @Override
      void addClips() {
        Boolean.this.addClips();
      }
    };
  }

  public Number select(Number trueNumber, Number falseNumber) {
    return new Number(() -> get() ? trueNumber.get() : falseNumber.get()) {
      @Override
      void addClips() {
        Boolean.this.addClips();
        trueNumber.maybeAddClips();
        falseNumber.maybeAddClips();
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
