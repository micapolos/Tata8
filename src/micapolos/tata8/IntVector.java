package micapolos.tata8;

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

  public ReadOnlyVector toReadonly() {
    return new ReadOnlyVector() {
      @Override
      public int x() {
        return x;
      }

      @Override
      public int y() {
        return y;
      }
    };
  }

  public FinalVector toFinal() {
    return new FinalVector(x, y);
  }

  @Override
  public String toString() {
    return "vector(" + x + ", " + y + ")";
  }
}
