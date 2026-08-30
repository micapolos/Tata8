package micapolos.tata8.model;

public abstract class Number {
  abstract double get();

  public static Number constant(double d) {
    return new Number() {
      @Override
      double get() {
        return d;
      }
    };
  }
}
