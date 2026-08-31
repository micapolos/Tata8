package micapolos.tata8.model;

import java.util.ArrayList;
import java.util.List;

import static micapolos.tata8.Game.keys;
import static micapolos.tata8.Game.onUpdate;
import static micapolos.tata8.model.Number.clippedSeconds;
import static micapolos.tata8.model.Number.number;

public final class Game {
  static final List<Action> initActions = new ArrayList<>();
  static final List<Animation> animations = new ArrayList<>();

  static final Size size = new Size(number(micapolos.tata8.Game.size.width), number(micapolos.tata8.Game.size.height));

  static void addInit(Action initAction) {
    initActions.add(initAction);
  }

  static void add(Animation animation) {
    animations.add(animation);
  }

  static void add(Clip clip) {
    initActions.add(clip::start);
    animations.add(clip::advance);
  }

  public static final Mouse mouse = new Mouse();

  static boolean startedValue;
  public static Event started = () -> startedValue;

  public static final Number seconds;

  static {
    var clippedSeconds = clippedSeconds();
    add(clippedSeconds.clip);
    seconds = clippedSeconds.value;
  }

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

  static void init() {
    startedValue = true;
    for (Action initAction : initActions) {
      initAction.execute();
    }
  }

  static void update() {
    if (keys.reset.pressed()) {
      init();
    }
    for (Animation animation : animations) {
      animation.update(1/60f);
    }
    startedValue = false;
  }

  public static void show() {
    init();
    onUpdate = Game::update;
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
