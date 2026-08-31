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
  public static Event started = () -> startedValue;

  public static void when(Event event, Action action) {
    add(new Clip() {
      @Override
      void start() {

      }

      @Override
      float advance(float seconds) {
        if (event.didHappen()) {
          action.execute();
        }
        return 0;
      }
    });
  }

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

  static void main() {
    Integer counter = Integer.variable();
    Integer increment = Integer.variable(1);
    Game.when(Key.Z.press, counter.add(increment));
    Game.when(Key.X.press, increment.add(1));
    counter.show();
  }
}
