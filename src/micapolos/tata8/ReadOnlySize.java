package micapolos.tata8;

import java.util.function.IntSupplier;

public abstract class ReadOnlySize {
  public abstract int width();
  public abstract int height();

  public final int area() {
    return Size.area(width(), height());
  }

  public static ReadOnlySize with(IntSupplier x, IntSupplier y) {
    return new ReadOnlySize() {
      @Override
      public int width() {
        return x.getAsInt();
      }

      @Override
      public int height() {
        return y.getAsInt();
      }
    };
  }

  public FinalSize toFinal() {
    return new FinalSize(width(), height());
  }
}
