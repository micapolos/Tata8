package micapolos.tata8.model;

public abstract class NumberVariable extends Number {
  abstract void set(double value);

  public static NumberVariable create(double initial) {
    return new NumberVariable() {
      double value = initial;

      @Override
      void set(double value) {
        this.value = value;
      }

      @Override
      double get() {
        return value;
      }
    };
  }

  public final void add(double d) {
    set(get() + d);
  }

  static void main() {
    NumberVariable variable = create(0);
    Game.add(() -> variable.add(1));
    variable.show();
  }
}

