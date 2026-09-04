package micapolos.zexy;

import micapolos.tata8.Math;
import micapolos.tata8.Shader;

import java.util.ArrayList;
import java.util.List;

import static micapolos.tata8.Game.*;
import static micapolos.zexy.Camera.*;
import static micapolos.zexy.Event.*;
import static micapolos.zexy.Integer.*;
import static micapolos.zexy.Number.*;

public final class Game {
  static final List<Runner> runners = new ArrayList<>();

  static final Size size =
    new Size(
      number(micapolos.tata8.Game.size.width),
      number(micapolos.tata8.Game.size.height));

  static void add(Animation animation) {
    add(new Runner() {
      @Override
      public void init() {
        animation.start();
      }

      @Override
      public void update(float seconds) {
        animation.step(seconds);
      }
    });
  }

  static void add(Runner runner) {
    runners.add(runner);
  }

  public static final Mouse mouse = new Mouse();

  static boolean startedValue;
  public static final Event start = event(() -> startedValue);

  public static final Number dusk = newNumber();

  public static void on(Event event, Action action) {
    add(new Animation() {
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
    event.addRunnersOnce();
    action.addRunnersOnce();
  }

  static void init() {
    Camera.camera.addRunnersOnce();
    micapolos.tata8.Game.screen.shader = Shader.CRT_PHOSPHOR;
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

  static float speedValue = 1;

  static void update() {
    speedValue = Math.elastic(speedValue,
      keys.slow.isPressed
        ? keys.fast.isPressed ? 1f : 0.125f
        : keys.fast.isPressed ? 8f : 1f, 0.1f);
    //micapolos.tata8.Game.dusk = (float) dusk.get();
    step(speedValue / 60);
  }

  public static void show() {
    init();
    onUpdate = Game::update;
    micapolos.tata8.Game.start();
  }

  static void main() {
    Integer counter = newInteger();
    Integer increment = newInteger();
    Game.on(Key.Z.press, counter.add(increment));
    Game.on(Key.X.press, increment.add(1));
    counter.show();
  }
}
