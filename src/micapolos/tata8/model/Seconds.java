package micapolos.tata8.model;

import static micapolos.tata8.model.clipped.ClippedDoubleValues.clippedSeconds;

public final class Seconds {
  public static final DoubleValue seconds;

  static {
    var clippedSeconds = clippedSeconds();
    clippedSeconds.maybeAddToGame();
    seconds = clippedSeconds.value;
  }
}
