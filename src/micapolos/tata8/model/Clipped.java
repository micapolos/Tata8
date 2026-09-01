package micapolos.tata8.model;

import micapolos.tata8.model.clipped.ClippedDoubleValues;

import java.util.function.*;

public abstract class Clipped<T extends Showable> implements Showable {
  public final T value;
  boolean added;

  Clipped(T value) {
    this.value = value;
  }

  abstract void addToGame();

  final void maybeAddToGame() {
    if (!added) {
      addToGame();
      added = true;
    }
  }

  public static <T extends Showable> Clipped<T> clipped(T value, Clip clip) {
    return new Clipped<>(value) {
      @Override
      void addToGame() {
        Game.add(clip);
      }
    };
  }

  public final Clipped<T> update(UnaryOperator<T> operator) {
    return new Clipped<T>(operator.apply(value)) {
      @Override
      void addToGame() {
        Clipped.this.maybeAddToGame();
      }
    };
  }

  public final Clipped<T> update(Clipped<T> x, BinaryOperator<T> operator) {
    return new Clipped<T>(operator.apply(value, x.value)) {
      @Override
      void addToGame() {
        Clipped.this.maybeAddToGame();
        x.maybeAddToGame();
      }
    };
  }

  public final <R extends Showable> Clipped<R> map(Function<T, R> function) {
    return new Clipped<>(function.apply(value)) {
      @Override
      void addToGame() {
        Clipped.this.maybeAddToGame();
      }
    };
  }

  public final <V extends Showable, R extends Showable> Clipped<R> map(Clipped<V> clipped, BiFunction<T, V, R> function) {
    return new Clipped<>(function.apply(value, clipped.value)) {
      @Override
      void addToGame() {
        Clipped.this.maybeAddToGame();
        clipped.maybeAddToGame();
      }
    };
  }

  @Override
  public final String toString() {
    return value.toString();
  }

  @Override
  public final void show() {
    maybeAddToGame();
    value.show();
  }

  static void main() {
    var s1 = ClippedDoubleValues.clippedSeconds();
    var s2 = ClippedDoubleValues.clippedSeconds();
    var s12 = ClippedDoubleValues.plus(s1, s2);
    s12.maybeAddToGame();
    Game.init();
    Game.step(10);
    IO.println("Should be 10: " + s1.value.get());
    IO.println("Should be 10: " + s2.value.get());
    IO.println("Should be 20: " + s12.value.get());
  }
}
