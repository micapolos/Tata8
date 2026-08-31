package micapolos.tata8.model;

import java.util.ArrayList;
import java.util.List;

import static micapolos.tata8.Game.keys;
import static micapolos.tata8.Game.onUpdate;
import static micapolos.tata8.model.Number.number;

public final class Game {
  static final List<Clip> clips = new ArrayList<>();
  static final Size size = new Size(number(micapolos.tata8.Game.size.width), number(micapolos.tata8.Game.size.height));

  static void add(Clip clip) {
    clips.add(clip);
  }

  public static final Mouse mouse = new Mouse();

  static boolean startedValue;
  static Event started = () -> startedValue;

  public static void show() {
    startedValue = true;
    for (Clip clip : clips) {
      clip.start();
    }
    onUpdate = () -> {
      if (keys.reset.pressed()) {
        startedValue = true;
        for (Clip clip : clips) {
          clip.start();
        }
      }
      for (Clip clip : clips) {
        clip.advance(1/60f);
      }
      startedValue = false;
    };
    micapolos.tata8.Game.start();
  }
}
