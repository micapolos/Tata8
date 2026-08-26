package micapolos.tata8;

public abstract class WriteOnlySize {
  public abstract void setWidth(int width);
  public abstract void setHeight(int height);

  public final void set(int width, int height) {
    setWidth(width);
    setHeight(height);
  }

  public final void set(FinalSize size) {
    setWidth(size.width);
    setHeight(size.height);
  }

  public final void set(ReadOnlySize size) {
    setWidth(size.width());
    setHeight(size.height());
  }

  public final void set(Size size) {
    setWidth(size.width);
    setHeight(size.height);
  }
}
