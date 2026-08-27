package micapolos.tata8;

public final class Size {
  public int width;
  public int height;

  public Size() {}

  public Size(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public void set(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public void set(Size size) {
    this.width = size.width;
    this.height = size.height;
  }

  public ReadOnlySize toReadOnly() {
    return new ReadOnlySize() {
      @Override
      public int width() {
        return width;
      }

      @Override
      public int height() {
        return height;
      }
    };
  }

  public WriteOnlySize toWriteOnly() {
    return new WriteOnlySize() {
      @Override
      public void setWidth(int newWidth) {
        width = newWidth;
      }

      @Override
      public void setHeight(int newHeight) {
        height = newHeight;
      }
    };
  };

  public FinalSize toFinal() {
    return new FinalSize(width, height);
  }

  public static int area(int width, int height) {
    return width * height;
  }

  public int area() {
    return area(width, height);
  }

  @Override
  public String toString() {
    return String.format("size(%d,%d)", width, height);
  }
}
