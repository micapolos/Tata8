package micapolos.zexy;

import micapolos.BooleanBinaryOperator;
import micapolos.BooleanUnaryOperator;

import java.util.function.BooleanSupplier;
import java.util.function.IntUnaryOperator;

import static micapolos.Leo.*;
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

  boolean get() {
    BooleanSupplier supplier = this.currentSupplier;
    return supplier != null ? supplier.getAsBoolean() : currentValue;
  }

  public static Boolean bool(boolean b) {
    return bool(() -> b);
  }

  static Boolean bool(BooleanSupplier supplier) {
    return new Boolean(supplier);
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

  public Boolean update(BooleanUnaryOperator operator) {
    return new Boolean(() -> operator.apply(get())) {
      @Override
      void addRunners() {
        Boolean.this.addRunnersOnce();
      }
    };
  }

  public Boolean update(Boolean bool, BooleanBinaryOperator operator) {
    return new Boolean(() -> operator.apply(get(), bool.get())) {
      @Override
      void addRunners() {
        Boolean.this.addRunnersOnce();
        bool.addRunnersOnce();
      }
    };
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

  public Boolean equals(Boolean bool) {
    return update(bool, (a, b) -> a == b);
  }

  public Boolean negated() {
    return not(this);
  }

  public static Boolean not(Boolean value) {
    return value.update(a -> !a);
  }

  public Boolean and(Boolean bool) {
    return update(bool, (a, b) -> a && b);
  }

  public Boolean or(Boolean bool) {
    return update(bool, (a, b) -> a || b);
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

  @Deprecated(forRemoval = true)
  public <T> Value<T> selectValue(T trueValue, T falseValue) {
    return new Value<>(() -> get() ? trueValue : falseValue) {
      @Override
      void addRunners() {
        Boolean.this.addRunners();
      }
    };
  }

  @Deprecated(forRemoval = true)
  public <T> Value<T> select(Value<T> trueValue, Value<T> falseValue) {
    return new Value<>(() -> get() ? trueValue.get() : falseValue.get()) {
      @Override
      void addRunners() {
        Boolean.this.addRunners();
      }
    };
  }

  @Deprecated(forRemoval = true)
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

  @Deprecated(forRemoval = true)
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

  public final Boolean isEqualTo(boolean i) {
    return isEqualTo(bool(i));
  }

  public final Boolean isEqualTo(Boolean bool) {
    return new Boolean(() -> get() == bool.get()) {
      @Override
      void addRunners() {
        Boolean.this.addRunnersOnce();
        bool.addRunnersOnce();
      }
    };
  }

  public final Event change() {
    return new Event() {
      boolean previous;

      @Override
      void addRunners() {
        Boolean.this.addRunnersOnce();

        Game.add(new Runner() {
          @Override
          public void init() {
            previous = Boolean.this.get();
            defaultOccurs = false;
          }

          @Override
          public void update(float seconds) {
            boolean current = Boolean.this.get();
            defaultOccurs = current != previous;
            previous = current;
          }
        });
      }
    };
  }

  public Event changeTo(boolean b) {
    return changeTo(bool(b));
  }

  public Event changeTo(Boolean bool) {
    return change().and(isEqualTo(bool));
  }

  public Boolean logged() {
    return update(n -> {
      micapolos.tata8.Game.log(Boolean.this);
      return n;
    });
  }

  public Boolean loggedWith(String label) {
    return update(n -> {
      micapolos.tata8.Game.log(leo(label, Boolean.this));
      return n;
    });
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }

  static void main() {
    numberOfSeconds.startLogging().show();
  }
}
