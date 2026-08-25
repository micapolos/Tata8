package micapolos;

public class Random {
  public static int between(int min, int max) {
    return (int) Math.round(min + Math.random() * (max - min));
  }

  public static int until(int limit) {
    return between(0, limit - 1);
  }
}
