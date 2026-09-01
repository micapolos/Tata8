package micapolos.tata8.model;

import micapolos.tata8.model.live.LiveNumbers;

import java.util.function.*;

public abstract class Live<T extends Showable> implements Showable {
  public final T value;
  boolean added;

  Live(T value) {
    this.value = value;
  }

  abstract void addToGame();

  final void maybeAddToGame() {
    if (!added) {
      addToGame();
      added = true;
    }
  }

  public static <T extends Showable> Live<T> live(T value, Clip clip) {
    return new Live<>(value) {
      @Override
      void addToGame() {
        Game.add(clip);
      }
    };
  }

  public final Live<T> update(UnaryOperator<T> operator) {
    return new Live<T>(operator.apply(value)) {
      @Override
      void addToGame() {
        Live.this.maybeAddToGame();
      }
    };
  }

  public final Live<T> update(Live<T> x, BinaryOperator<T> operator) {
    return new Live<T>(operator.apply(value, x.value)) {
      @Override
      void addToGame() {
        Live.this.maybeAddToGame();
        x.maybeAddToGame();
      }
    };
  }

  public final <R extends Showable> Live<R> map(Function<T, R> function) {
    return new Live<>(function.apply(value)) {
      @Override
      void addToGame() {
        Live.this.maybeAddToGame();
      }
    };
  }

  public final <V extends Showable, R extends Showable> Live<R> map(Live<V> live, BiFunction<T, V, R> function) {
    return new Live<>(function.apply(value, live.value)) {
      @Override
      void addToGame() {
        Live.this.maybeAddToGame();
        live.maybeAddToGame();
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
    var s1 = LiveNumbers.liveSeconds();
    var s2 = LiveNumbers.liveSeconds();
    var s12 = LiveNumbers.plus(s1, s2);
    s12.maybeAddToGame();
    Game.init();
    Game.step(10);
    IO.println("Should be 10: " + s1.value.get());
    IO.println("Should be 10: " + s2.value.get());
    IO.println("Should be 20: " + s12.value.get());
  }
}
