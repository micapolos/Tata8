package micapolos.tata8;

public final class FinalSize {
  public final int width;
  public final int height;

  public FinalSize(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public int area() {
    return Size.area(width, height);
  }

  public static FinalSize zero() {
    return new FinalSize(0, 0);
  }

  public FinalSize plus(int width, int height) {
    return new FinalSize(this.width + width, this.height + height);
  }

  public FinalSize plus(FinalSize size) {
    return plus(size.width, size.height);
  }

  public FinalSize minus(int width, int height) {
    return new FinalSize(this.width + width, this.height + height);
  }

  public FinalSize minus(FinalSize size) {
    return minus(size.width, size.height);
  }

  @Override
  public String toString() {
    return String.format("size(%d, %d)", width, height);
  }
}
