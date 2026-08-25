package micapolos;

public final class IntVector {
  public int x;
  public int y;

  public void set(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void set(IntVector v) {
    set(v.x, v.y);
  }

  public void add(int x, int y) {
    this.x += x;
    this.y += y;
  }

  public void add(IntVector v) {
    add(v.x, v.y);
  }
}
