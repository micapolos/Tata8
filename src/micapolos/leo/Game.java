package micapolos.leo;

import java.util.ArrayList;
import java.util.List;

import static micapolos.tata8.Game.keys;
import static micapolos.tata8.Game.onUpdate;
import static micapolos.leo.Action.action;
import static micapolos.leo.Event.event;
import static micapolos.leo.Number.number;

public final class Game {
  static final List<Action> initActions = new ArrayList<>();
  static final List<Stepper> steppers = new ArrayList<>();
  static final List<Runner> runners = new ArrayList<>();

  static final Size size =
    new Size(
      number(micapolos.tata8.Game.size.width),
      number(micapolos.tata8.Game.size.height));

  static void addInit(Action initAction) {
    initActions.add(initAction);
  }

  static void add(Stepper stepper) {
    steppers.add(stepper);
  }

  static void add(Clip clip) {
    initActions.add(action("init clip", clip::start));
    steppers.add(clip::step);
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
    for (Action initAction : initActions) {
      initAction.execute();
    }
  }

  static void step(float seconds) {
    if (keys.reset.pressed()) {
      init();
    }
    for (Runner runner : runners) {
      runner.update(seconds);
    }
    for (Stepper stepper : steppers) {
      float unusedOverflow = stepper.step(seconds);
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
