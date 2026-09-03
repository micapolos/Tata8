package micapolos.zexy;

import java.util.function.BooleanSupplier;

import static micapolos.zexy.Animation.*;
import static micapolos.zexy.Integer.*;
import static micapolos.zexy.Number.*;
import static micapolos.zexy.Value.*;

public class Boolean extends ValueComponent {
  BooleanSupplier currentSupplier;
  boolean currentValue;

  Boolean() {

  }

  Boolean(BooleanSupplier supplier) {
    currentSupplier = supplier;
    animation = noAnimation;
  }

  Boolean(Animation animation) {
    this.animation = animation;
  }

  public boolean get() {
    BooleanSupplier supplier = this.currentSupplier;
    return supplier != null ? supplier.getAsBoolean() : currentValue;
  }

  public static Boolean bool(boolean b) {
    return bool(() -> b);
  }

  public static Boolean bool(BooleanSupplier supplier) {
    return new Boolean(supplier);
  }

  public static Boolean bool(Animation animation) {
    return new Boolean(animation);
  }

  public static Boolean newBoolean() {
    return new Boolean();
  }

  void setImmediately(boolean x) {
    setImmediately(null, x);
  }

  void setImmediately(Boolean number) {
    setImmediately(number::get, false);
  }

  void setImmediately(BooleanSupplier supplier, boolean defaultValue) {
    this.currentSupplier = supplier;
    this.currentValue = defaultValue;
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
    return new Boolean() {
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

  public <T> Value<T> select(Value<T> trueValue, Value<T> falseValue) {
    return new Value<>(() -> get() ? trueValue.get() : falseValue.get()) {
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

  public Integer select(Integer trueInteger, Integer falseInteger) {
    return new Integer(() -> get() ? trueInteger.get() : falseInteger.get()) {
      @Override
      void addRunners() {
        Boolean.this.addRunners();
        trueInteger.addRunnersOnce();
        falseInteger.addRunnersOnce();
      }
    };
  }

  public <T extends Component> IfTrue.WithComponent<T> ifTrue(T t) {
    return ifTrue(value(t));
  }

  public <T extends Component> IfTrue.WithComponent<T> ifTrue(Value<T> value) {
    return new IfTrue.WithComponent<>(this, value);
  }

  public IfTrue.WithNumber ifTrue(double d) {
    return ifTrue(number(d));
  }

  public IfTrue.WithNumber ifTrue(Number number) {
    return new IfTrue.WithNumber(this, number);
  }

  public IfTrue.WithInteger ifTrue(int i) {
    return ifTrue(integer(i));
  }

  public IfTrue.WithInteger ifTrue(Integer integer) {
    return new IfTrue.WithInteger(this, integer);
  }

  public Number toNumber() {
    return new Number(() -> get() ? 1 : 0) {
      @Override
      void addRunners() {
        Boolean.this.addRunnersOnce();
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
