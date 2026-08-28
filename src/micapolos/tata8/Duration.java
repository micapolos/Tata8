package micapolos.tata8;

public final class Duration {
  final float seconds;

  Duration(float seconds) {
    this.seconds = seconds;
  }

  public static Duration seconds(float s) {
    return new Duration(s);
  }
}
