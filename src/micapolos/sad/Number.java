package micapolos.sad;

import static micapolos.Leo.*;

public abstract class Number extends Component {
  abstract double get();

  public static Number number(double d) {
    return new Number() {
      @Override
      double get() {
        return d;
      }

      @Override
      String toLeo() {
        return String.valueOf(d);
      }
    };
  }

  public static abstract class Value extends Number {
    double value;

    @Override
    double get() {
      return value;
    }
  }

  public static abstract class Variable extends Value {}

  public final Number plus(Number number) {
    return new Value() {
      @Override
      void update() {
        value = Number.this.get() + number.get();
      }

      @Override
      void compileDeps(Compiler compiler) {
        compiler.compile(Number.this);
        compiler.compile(number);
      }

      @Override
      String toLeo() {
        return leo("plus", Number.this, number);
      }
    };
  }

  public final Number times(Number number) {
    return new Value() {
      @Override
      void update() {
        value = Number.this.get() * number.get();
      }

      @Override
      void compileDeps(Compiler compiler) {
        compiler.compile(Number.this);
        compiler.compile(number);
      }

      @Override
      String toLeo() {
        return leo("times", Number.this, number);
      }
    };
  }

  public static final Number seconds = new Value() {
    @Override
    void init() {
      value = 0;
    }

    @Override
    float step(float seconds) {
      value += seconds;
      return 0;
    }

    @Override
    String toLeo() {
      return leo("seconds");
    }
  };

  public final Number logging() {
    return new Number() {
      @Override
      double get() {
        return Number.this.get();
      }

      @Override
      void update() {
        IO.println(get());
      }

      @Override
      void compileDeps(Compiler compiler) {
        compiler.compile(Number.this);
      }

      @Override
      String toLeo() {
        return leo("logging", Number.this);
      }
    };
  }

  public Component set(Number number) {
    Variable variable = (Variable) this;

    return new Component() {
      @Override
      void update() {
        variable.value = number.get();
      }

      @Override
      void compileDeps(Compiler compiler) {
        compiler.compile(Number.this);
        compiler.compile(number);
      }

      @Override
      String toLeo() {
        return leo("set", Number.this, number);
      }
    };
  }

  static void main() {
    var seconds = Number.seconds.logging();
    var one = number(1).logging();
    var plusSeconds = one.plus(seconds).logging();
    var two = plusSeconds.plus(plusSeconds).logging();
    var four = two.plus(two).logging();
    var five = four.plus(one).logging();
    var ten = five.plus(five).logging();
    var eleven = ten.plus(one).logging();
    var root = eleven.compile();

    IO.println("=== Init");
    root.init();
    IO.println("=== Update");
    root.update();
    IO.println("=== Step");
    root.step(10);
    IO.println("=== Update");
    root.update();
  }
}
