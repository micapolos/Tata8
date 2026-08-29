package micapolos.tata8;

public class Random {
  private Random() {}

  public static int between(float min, float max) {
    return (int) Math.round(min + Math.random() * (max - min));
  }

  public static int until(float limit) {
    return between(0, limit - 1);
  }
}
