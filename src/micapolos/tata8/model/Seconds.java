package micapolos.tata8.model;

import static micapolos.tata8.model.live.LiveNumbers.liveSeconds;

public final class Seconds {
  public static final Number seconds;

  static {
    var liveSeconds = liveSeconds();
    liveSeconds.maybeAddToGame();
    seconds = liveSeconds.value;
  }
}
