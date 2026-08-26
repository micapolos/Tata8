package micapolos.tata8;

public abstract class WriteOnlyVector {
  public abstract void setX(int x);
  public abstract void setY(int y);

  public final void set(int x, int y) {
    setX(x);
    setY(y);
  }
}
