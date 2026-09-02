package micapolos.zexy;

import java.util.ArrayList;
import java.util.List;

import static micapolos.tata8.Game.*;
import static micapolos.zexy.Event.*;
import static micapolos.zexy.Number.*;

public final class Game {
  static final List<Runner> runners = new ArrayList<>();

  static final Size size =
    new Size(
      number(micapolos.tata8.Game.size.width),
      number(micapolos.tata8.Game.size.height));

  static void add(Clip clip) {
    add(new Runner() {
      @Override
      public void init() {
        clip.start();
      }

      @Override
      public void update(float seconds) {
        clip.step(seconds);
      }
    });
  }

  static void add(Runner runner) {
    runners.add(runner);
  }

  public static final Mouse mouse = new Mouse();

  static boolean startedValue;
  public static final Event start = event(() -> startedValue);

  public static void on(Event event, Action action) {
    add(new Clip() {
      @Override
      void start() {

      }

      @Override
      float step(float seconds) {
        if (event.occurs()) {
          action.execute();
        }
        return 0;
      }
    });
  }

  static void init() {
    startedValue = true;
    for (Runner runner : runners) {
      runner.init();
    }
  }

  static void step(float seconds) {
    if (keys.reset.pressed()) {
      init();
    }
    for (Runner runner : runners) {
      runner.update(seconds);
    }
    startedValue = false;
  }

  static void update() {
    step(1/60f);
  }

  public static void show() {
    init();
    onUpdate = Game::update;
    micapolos.tata8.Game.start();
  }

  static void main() {
    Integer counter = Integer.newInteger();
    Integer increment = Integer.newInteger(1);
    Game.on(Key.Z.press, counter.add(increment));
    Game.on(Key.X.press, increment.add(1));
    counter.show();
  }
}
