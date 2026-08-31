package micapolos;

import java.util.function.Function;

public final class Blocks {
  public static <T, R> R ifNotNull(T t, Function<T, R> fn) {
    return t != null ? fn.apply(t) : null;
  }

  private Blocks() {}
}
