package micapolos.tata8.model;

import micapolos.tata8.Image;

public class Action extends Component {
  void execute() {}

  public static final Action noAction = new Action();

  public static Action action(Runnable runnable) {
    return new Action() {
      @Override
      void execute() {
        runnable.run();
      }
    };
  }

  public static Action set(micapolos.tata8.Sprite sprite, Image image) {
    return new Action() {
      @Override
      void execute() {
        sprite.image = image;
      }
    };
  }

  public static Action setX(micapolos.tata8.Position position, float x) {
    return new Action() {
      @Override
      void execute() {
        position.x += x;
      }
    };
  }

  public static Action setY(micapolos.tata8.Position position, float y) {
    return new Action() {
      @Override
      void execute() {
        position.y += y;
      }
    };
  }

  public static Action sequence(Action... actions) {
    return new Action() {
      @Override
      void execute() {
        for (Action action : actions) {
          action.execute();
        }
      }

      @Override
      void addClips() {
        for (Action action : actions) {
          action.maybeAddClips();
        }
      }
    };
  }

  public Action then(Action action) {
    return new Action() {
      @Override
      void execute() {
        Action.this.execute();
        action.execute();
      }

      @Override
      void addClips() {
        Action.this.maybeAddClips();
        action.maybeAddClips();
      }
    };
  }
}
