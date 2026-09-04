package micapolos.zexy;

import micapolos.DoubleBinaryPredicate;
import micapolos.DoubleUtils;

import java.util.Locale;
import java.util.function.*;

import static micapolos.Leo.*;
import static micapolos.zexy.Animation.*;

public class Number extends ValueComponent {
  DoubleSupplier currentSupplier;
  double currentValue;

  Number() {
    this(0);
  }

  Number(double d) {
    this(null, d);
  }

  Number(DoubleSupplier supplier) {
    this(supplier, 0);
  }

  Number(DoubleSupplier supplier, double currentValue) {
    this.animation = noAnimation;
    this.currentSupplier = supplier;
    this.currentValue = currentValue;
  }

  Number(Animation animation) {
    this.animation = animation;
  }

  double get() {
    DoubleSupplier supplier = this.currentSupplier;
    return supplier != null ? supplier.getAsDouble() : currentValue;
  }

  public static Number animatedNumber(Animator<Number> animator) {
    var number = newNumber();
    number.init(animator.animate(number));
    return number;
  }

  public Number keep(Activity activity) {
    return new Number(start(activity)) {
      @Override
      void addRunners() {
        Number.this.addRunnersOnce();
      }
    };
  }

  public static final Number randomNumber = randomNumber();

  static Number randomNumber() {
    return number(Math::random);
  }

  public static final Number numberOfSeconds = new Number(noAnimation) {
    @Override
    void addRunners() {
      Game.add(new Runner() {
        @Override
        public void init() {
          currentValue = 0;
        }

        @Override
        public void update(float seconds) {
          currentValue += seconds;
        }
      });
    }
  };

  public static final Number numberZero = number(0);
  public static final Number numberHalf = number(0.5f);
  public static final Number numberOne = number(1);

  public static Number number(double value) {
    return new Number(value);
  }

  static Number number(DoubleSupplier supplier) {
    return new Number(supplier);
  }

  public static Number number(Number number) {
    return new Number(number::get) {
      @Override
      void addRunners() {
        number.addRunnersOnce();
      }
    };
  }

  public static Number newNumber() {
    return newNumber(0);
  }

  public static Number newNumber(double d) {
    return newNumber(number(d));
  }

  public static Number newNumber(Number number) {
    return new Number(number::get) {
      {
        animation = null;
      }

      @Override
      void addRunners() {
        number.addRunners();
      }
    };
  }

  public Number toReadonly() {
    return isReadonly() ? this : number(this);
  }

  public Number update(DoubleUnaryOperator operator) {
    return new Number(() -> operator.applyAsDouble(get())) {
      @Override
      void addRunners() {
        Number.this.addRunnersOnce();
      }
    };
  }

  public Number update(Number number, DoubleBinaryOperator operator) {
    return new Number(() -> operator.applyAsDouble(get(), number.get())) {
      @Override
      void addRunners() {
        Number.this.addRunnersOnce();
        number.addRunnersOnce();
      }
    };
  }

  public Action mapToAction(Runnable runnable) {
    checkVariable();
    return new Action() {
      @Override
      void execute() {
        runnable.run();
      }

      @Override
      void addRunners() {
        Number.this.addRunnersOnce();
      }
    };
  }

  public Action mapToAction(Component component, Runnable runnable) {
    checkVariable();
    return new Action() {
      @Override
      void execute() {
        runnable.run();
      }

      @Override
      void addRunners() {
        Number.this.addRunnersOnce();
        component.addRunnersOnce();
      }
    };
  }

  public <R> Value<R> mapToValue(DoubleFunction<R> function) {
    return new Value<R>(() -> function.apply(get())) {
      @Override
      void addRunners() {
        Number.this.addRunnersOnce();
      }
    };
  }

  public Integer mapToInteger(DoubleToIntFunction function) {
    return new Integer(() -> function.applyAsInt(get())) {
      @Override
      void addRunners() {
        Number.this.addRunnersOnce();
      }
    };
  }

  public Boolean mapToBool(DoublePredicate function) {
    return new Boolean(() -> function.test(get())) {
      @Override
      void addRunners() {
        Number.this.addRunnersOnce();
      }
    };
  }

  public Boolean mapToBool(Number number, DoubleBinaryPredicate predicate) {
    return new Boolean(() -> predicate.test(get(), number.get())) {
      @Override
      void addRunners() {
        Number.this.addRunnersOnce();
        number.addRunnersOnce();
      }
    };
  }

  public Number negated() {
    return update(DoubleUtils::negated);
  }

  public Number plus(double x) {
    return update(d -> d + x);
  }

  public Number plus(Number n) {
    return update(n, DoubleUtils::plus);
  }

  public Number minus(double x) {
    return update(d -> d - x);
  }

  public Number minus(Number n) {
    return update(n, DoubleUtils::minus);
  }

  public Number times(double x) {
    return update(d -> d * x);
  }

  public Number times(Number n) {
    return update(n, DoubleUtils::times);
  }

  public Number fraction() {
    return update(DoubleUtils::fraction);
  }

  public Number floor() {
    return update(Math::floor);
  }

  public Number ceil() {
    return update(Math::ceil);
  }

  public Number round() {
    return update(Math::round);
  }

  public Integer toInteger() {
    return mapToInteger(d -> (int) d);
  }

  void setImmediately(double x) {
    setImmediately(null, x);
  }

  void setImmediately(Number number) {
    setImmediately(number::get, 0);
  }

  void setImmediately(DoubleSupplier supplier, double defaultValue) {
    this.currentSupplier = supplier;
    this.currentValue = defaultValue;
  }

  public Action set(double x) {
    return mapToAction(() -> setImmediately(x));
  }

  public Action set(Number number) {
    return new Action() {
      @Override
      void execute() {
        setImmediately(number);
      }

      @Override
      void addRunners() {
        number.addRunnersOnce();
      }
    };
  }

  public Action capture(Number number) {
    return mapToAction(number, () -> setImmediately(number.get()));
  }

  public Action add(double d) {
    return mapToAction(() -> setImmediately(get() + d));
  }

  public Action add(Number n) {
    return mapToAction(n, () -> setImmediately(get() + n.get()));
  }

  public Activity adding(double speed) {
    return adding(number(speed));
  }

  public Activity adding(Number speed) {
    return new Activity() {
      @Override
      void advance(float seconds) {
        setImmediately(get() + speed.get() * seconds);
      }

      @Override
      void addRunners() {
        Number.this.addRunnersOnce();
      }

      @Override
      public String toString() {
        return leo("moving", speed.get());
      }
    };
  }

  public Activity setting(double speed) {
    return setting(number(speed));
  }

  public Activity setting(Number number) {
    return new Activity() {
      @Override
      void advance(float seconds) {
        setImmediately(number.get());
      }

      @Override
      void addRunners() {
        Number.this.addRunnersOnce();
      }

      @Override
      public String toString() {
        return leo("setting", number.get());
      }
    };
  }

  public Activity subtracting(double speed) {
    return subtracting(number(speed));
  }

  public Activity subtracting(Number speed) {
    return adding(speed.negated());
  }

  public Number elastic() {
    return elastic(micapolos.tata8.Math.ELASTIC_FACTOR);
  }

  public Number elastic(double d) {
    return elastic(number(d));
  }

  public Number elastic(Number factor) {
    return new Number() {
      @Override
      void addRunners() {
        Number.this.addRunnersOnce();
        factor.addRunnersOnce();

        Game.add(new Runner() {
          @Override
          public void init() {
            currentValue = Number.this.get();
          }

          @Override
          public void update(float seconds) {
            // TODO: Make it dependent on seconds.
            currentValue = micapolos.tata8.Math.elastic((float) get(), (float) Number.this.get(), (float) factor.get());
          }
        });
      }
    };
  }

  public Number logged() {
    return update(n -> {
      micapolos.tata8.Game.log(Number.this);
      return n;
    });
  }

  public Number loggedAs(String label) {
    return update(n -> {
      micapolos.tata8.Game.log(leo(label, Number.this));
      return n;
    });
  }

  public final Boolean isEqualTo(double d) {
    return isEqualTo(number(d));
  }

  public final Boolean isEqualTo(Number number) {
    return mapToBool(number, (a, b) -> a == b);
  }

  public final Boolean isGreaterThan(double d) {
    return isGreaterThan(number(d));
  }

  public final Boolean isGreaterThan(Number number) {
    return mapToBool(number, (a, b) -> a > b);
  }

  public final Boolean isGreaterOrEqualThan(double d) {
    return isGreaterOrEqualThan(number(d));
  }

  public final Boolean isGreaterOrEqualThan(Number number) {
    return mapToBool(number, (a, b) -> a >= b);
  }

  public final Boolean isLessThan(double d) {
    return isLessThan(number(d));
  }

  public final Boolean isLessThan(Number number) {
    return mapToBool(number, (a, b) -> a < b);
  }

  public final Boolean isLessOrEqualThan(double d) {
    return isLessOrEqualThan(number(d));
  }

  public final Boolean isLessOrEqualThan(Number number) {
    return mapToBool(number, (a, b) -> a <= b);
  }

  public final Event change() {
    return new Event() {
      double previous;

      @Override
      void addRunners() {
        Number.this.addRunnersOnce();

        Game.add(new Runner() {
          @Override
          public void init() {
            previous = Number.this.get();
            defaultOccurs = false;
          }

          @Override
          public void update(float seconds) {
            double current = Number.this.get();
            defaultOccurs = current != previous;
            previous = current;
          }
        });
      }
    };
  }

  public Event changeTo(double d) {
    return changeTo(number(d));
  }

  public Event changeTo(Number number) {
    return change().and(isEqualTo(number));
  }

  @Override
  public String toString() {
    return String.format(Locale.ROOT, "%.3f", get());
  }

  static void main() {
    var number = number(numberOfSeconds);
    number.show();
  }
}
