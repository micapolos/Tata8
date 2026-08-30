package micapolos.tata8.model;

import java.util.function.DoubleSupplier;

public abstract class Position implements Showable {
  abstract double x();
  abstract double y();

  static Position with(micapolos.tata8.Position state) {
    return new Position() {
      @Override
      double x() {
        return state.x;
      }

      @Override
      double y() {
        return state.y;
      }
    };
  }

  @Override
  public String toString() {
    return String.format("position(x: %s, y: %s)", x(), y());
  }

  static void main() {
    new Position() {
      @Override
      public double x() {
        return 2;
      }

      @Override
      public double y() {
        return 3;
      }
    }.show();
  }
}
