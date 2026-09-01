package micapolos.tata8.model;

import static micapolos.tata8.model.Number.clippedSeconds;

public final class Seconds {
  public static final Number seconds;

  static {
    var clippedSeconds = clippedSeconds();
    clippedSeconds.addClips();
    seconds = clippedSeconds.value;
  }
}
