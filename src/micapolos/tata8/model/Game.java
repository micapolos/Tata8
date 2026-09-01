package micapolos.tata8.model;

import java.util.ArrayList;
import java.util.List;

import static micapolos.tata8.Game.keys;
import static micapolos.tata8.Game.onUpdate;
import static micapolos.tata8.model.Event.with;
import static micapolos.tata8.model.Number.with;

public final class Game {
  static final List<Action> initActions = new ArrayList<>();
  static final List<Stepper> steppers = new ArrayList<>();

  static final Size size = new Size(Number.with(micapolos.tata8.Game.size.width), Number.with(micapolos.tata8.Game.size.height));

  static void addInit(Action initAction) {
    initActions.add(initAction);
  }

  static void add(Stepper stepper) {
    steppers.add(stepper);
  }

  static void add(Clip clip) {
    initActions.add(clip::start);
    steppers.add(clip::step);
  }

  public static final Mouse mouse = new Mouse();

  static boolean startedValue;
  public static Event started = Event.with(() -> startedValue);

  public static void when(Event event, Action action) {
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
    for (Action initAction : initActions) {
      initAction.execute();
    }
  }

  static void step(float seconds) {
    if (keys.reset.pressed()) {
      init();
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
    Integer counter = Integer.newVariable();
    Integer increment = Integer.newVariable(1);
    Game.when(Key.Z.press, counter.add(increment));
    Game.when(Key.X.press, increment.add(1));
    counter.show();
  }
}
