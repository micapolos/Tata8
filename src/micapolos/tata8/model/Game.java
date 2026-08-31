package micapolos.tata8.model;

import java.util.ArrayList;
import java.util.List;

import static micapolos.tata8.Game.keys;
import static micapolos.tata8.Game.onUpdate;

public final class Game {
  static final List<Clip> clips = new ArrayList<>();

  static void add(Clip clip) {
    clips.add(clip);
  }

  public static final Mouse mouse = new Mouse();

  public static void show() {
    for (Clip clip : clips) {
      clip.start();
    }
    onUpdate = () -> {
      if (keys.reset.pressed()) {
        for (Clip clip : clips) {
          clip.start();
        }
      }
      for (Clip clip : clips) {
        clip.advance(1/60f);
      }
    };
    micapolos.tata8.Game.start();
  }
}
