package micapolos.tata8;

public final class Anchor {
  public float x;
  public float y;

  Anchor() {}

  public void set(float x, float y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return String.format("anchor(%d,%d)", x, y);
  }
}
