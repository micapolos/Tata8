package micapolos.tata8;

public final class FinalVector {
  public final int x;
  public final int y;

  public FinalVector(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public static FinalVector zero() {
    return new FinalVector(0, 0);
  }

  public FinalVector plus(int x, int y) {
    return new FinalVector(this.x + x, this.y + y);
  }

  public FinalVector plus(FinalVector vector) {
    return plus(vector.x, vector.y);
  }

  public FinalVector minus(int x, int y) {
    return new FinalVector(this.x - x, this.y - y);
  }

  public FinalVector minus(FinalVector vector) {
    return minus(vector.x, vector.y);
  }
}
