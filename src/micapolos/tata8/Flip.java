package micapolos.tata8;

public final class Flip {
  public boolean x;
  public boolean y;

  public void set(boolean x, boolean y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return String.format("flip(%s,%s)", x, y);
  }
}
