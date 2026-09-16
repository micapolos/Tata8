package micapolos.tata8;

public final class IntSize {
  public int width;
  public int height;

  public void set(IntSize size) {
    set(size.width, size.height);
  }

  public void set(int width, int height) {
    this.width = width;
    this.height = height;
  }
}
