package micapolos.tata8;

public class Random {
  private Random() {}

  public static float between(float min, float max) {
    return Math.round(min + Math.random() * (max - min));
  }

  public static float until(float limit) {
    return between(0, limit - 1);
  }
}
