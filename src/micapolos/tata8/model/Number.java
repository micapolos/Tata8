package micapolos.tata8.model;

import micapolos.DoubleUtils;

import java.util.Locale;
import java.util.function.*;

import static micapolos.tata8.Math.elastic;
import static micapolos.tata8.model.Clip.frame;
import static micapolos.tata8.model.Clip.pause;

public class Number extends ValueComponent {
  DoubleSupplier commitSupplier;
  double commitValue;

  DoubleSupplier currentSupplier;
  double currentValue;

  Number() {
    this(false, null, 0);
  }

  Number(double d) {
    this(false, null, d);
  }

  Number(DoubleSupplier supplier) {
    this(false, supplier, 0);
  }

  Number(Clip clip, DoubleSupplier supplier) {
    this(clip, false, supplier, 0);
  }

  Number(boolean isVariable, DoubleSupplier supplier, double currentValue) {
    this(Clip.emptyClip, isVariable, supplier, currentValue);
  }

  Number(Clip clip, boolean isVariable, DoubleSupplier supplier, double currentValue) {
    super(clip, isVariable);
    this.currentSupplier = supplier;
    this.currentValue = currentValue;
  }

  public double get() {
    DoubleSupplier supplier = this.currentSupplier;
    return supplier != null ? supplier.getAsDouble() : currentValue;
  }

  public Number with(Clip clip) {
    return new Number(clip, this::get);
  }

  public static final Number random = randomNumber();

  public static Number randomNumber() {
    return number(Math::random);
  }

  public static final Number seconds = new Number(false, null, 0) {
    @Override
    void addClips() {
      Game.add(new Runner() {
        @Override
        public void init() {
          commitValue = 0;
        }

        @Override
        public void update(float seconds) {
          commitValue += seconds;
        }

        @Override
        public void commit() {
          currentValue = commitValue;
        }
      });
    }
  };

  public static final Number zero = number(0);
  public static final Number half = number(0.5f);
  public static final Number one = number(1);

  public static Number number(double value) {
    return new Number(value);
  }

  static Number number(DoubleSupplier supplier) {
    return new Number(supplier);
  }

  public static Number newNumber() {
    return newNumber(0);
  }

  public static Number newNumber(double value) {
    return newNumber(() -> value);
  }

  public static Number newNumber(Number value) {
    return newNumber(value::get);
  }

  public static Number newNumber(DoubleSupplier aSupplier) {
    return new Number(true, aSupplier, 0) {
      @Override
      void addClips() {
        Game.add(new Runner() {
          @Override
          public void init() {
            commitSupplier = aSupplier;
            commitValue = 0;
          }

          @Override
          public void update(float seconds) {

          }

          @Override
          public void commit() {
            currentSupplier = commitSupplier;
            currentValue = commitValue;
          }
        });
      }
    };
  }

  public Number readonly() {
    return isVariable ? new Number(this::get) {
      @Override
      void addClips() {
        Number.this.maybeAddClips();
      }
    } : this;
  }

  public Number update(DoubleUnaryOperator operator) {
    return new Number(() -> operator.applyAsDouble(get())) {
      @Override
      void addClips() {
        Number.this.maybeAddClips();
      }
    };
  }

  public Number update(Number b, DoubleBinaryOperator operator) {
    return new Number(() -> operator.applyAsDouble(get(), b.get())) {
      @Override
      void addClips() {
        Number.this.maybeAddClips();
        b.maybeAddClips();
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
      void addClips() {
        Number.this.maybeAddClips();
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
      void addClips() {
        Number.this.maybeAddClips();
        component.maybeAddClips();
      }
    };
  }

  public <R> Value<R> mapToValue(DoubleFunction<R> function) {
    return new Value<R>(() -> function.apply(get())) {
      @Override
      void addClips() {
        Number.this.maybeAddClips();
      }
    };
  }

  public Integer mapToInteger(DoubleToIntFunction function) {
    return new Integer(() -> function.applyAsInt(get())) {
      @Override
      void addClips() {
        Number.this.maybeAddClips();
      }
    };
  }

  public Boolean mapToBool(DoublePredicate function) {
    return new Boolean(() -> function.test(get())) {
      @Override
      void addClips() {
        Number.this.maybeAddClips();
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

  public Integer integer() {
    return mapToInteger(d -> (int) d);
  }

  public void setImmediately(double x) {
    setImmediately(null, x);
  }

  public void setImmediately(Number number) {
    setImmediately(number::get, 0);
  }

  void setImmediately(DoubleSupplier supplier, double defaultValue) {
    checkVariable();
    this.currentSupplier = supplier;
    this.currentValue = defaultValue;
  }

  public Action set(double x) {
    return mapToAction(() -> setImmediately(x));
  }

  public Action set(Number number) {
    return mapToAction(number, () -> setImmediately(number));
  }

  public Action capture(Number number) {
    return mapToAction(number, () -> setImmediately(number.get()));
  }

  public Stepper setElastic(double n) {
    return setElastic(number(n));
  }

  public Stepper setElastic(Number number) {
    checkVariable();
    return seconds -> {
      setImmediately(elastic((float) get(), (float) number.get()));
      return seconds;
    };
  }

  public Action add(double d) {
    return mapToAction(() -> setImmediately(get() + d));
  }

  public Action add(Number n) {
    return mapToAction(n, () -> setImmediately(get() + n.get()));
  }

  @Override
  public String toString() {
    return String.format(Locale.ROOT, "%.3f", get());
  }

  static void main() {
    var number = newNumber();
    number.with(pause(1).then(frame(1, number.add(1)).repeat())).show();
  }
}
