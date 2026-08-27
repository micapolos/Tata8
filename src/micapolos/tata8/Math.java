package micapolos.tata8;

public final class Math {
  public static final float PI = (float) java.lang.Math.PI;
  public static final float TAU = PI * 2;

  public static int clamp(int i, int min, int max) {
    return java.lang.Math.clamp(i, min, max);
  }

  public static float clamp(float f, float min, float max) {
    return java.lang.Math.clamp(f, min, max);
  }

  public static float random() {
    return (float) java.lang.Math.random();
  }

  public static float floor(float f) {
    return (float) java.lang.Math.floor(f);
  }

  public static float ceil(float f) {
    return (float) java.lang.Math.ceil(f);
  }

  public static float round(float f) {
    return java.lang.Math.round(f);
  }

  public static float fract(float f) {
    return f - floor(f);
  }

  public static float elastic(float from, float to) {
    float next = from + (to - from) * 0.25f;
    return abs(next - from) <= 0.25f ? to : next;
  }

  public static float abs(float f) {
    return java.lang.Math.abs(f);
  }

  private Math() {}
}
