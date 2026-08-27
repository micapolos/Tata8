package micapolos.tata8;

public final class Scale {
  public float x = 1;
  public float y = 1;

  Scale() {}

  @Override
  public String toString() {
    return String.format("scale(%d,%d)", x, y);
  }

}
