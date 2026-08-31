package micapolos;

public final class DoubleUtils {
  public static double negated(double a) {
    return -a;
  }

  public static double plus(double a, double b) {
    return a + b;
  }

  public static double minus(double a, double b) {
    return a - b;
  }

  public static double times(double a, double b) {
    return a * b;
  }

  public static double fraction(double a) {
    return a - Math.floor(a);
  }

  public static int toInt(double a) {
    return (int) a;
  }
}
