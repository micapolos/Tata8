package micapolos.zexy;

import micapolos.tata8.Image;

import static micapolos.Leo.*;

public class Action extends Component {
  void execute() {}

  public static final Action noAction = new Action() {
    @Override
    public String toString() {
      return "no action";
    }
  };

  public static Action action(String name, Runnable runnable) {
    return new Action() {
      @Override
      void execute() {
        runnable.run();
      }

      @Override
      public String toString() {
        return name;
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
      void addRunners() {
        for (Action action : actions) {
          action.addRunnersOnce();
        }
      }

      @Override
      public String toString() {
        return leo("sequence", actions);
      }
    };
  }

  @Override
  public String toString() {
    return "an action";
  }

  public Action then(Action action) {
    return new Action() {
      @Override
      void execute() {
        Action.this.execute();
        action.execute();
      }

      @Override
      void addRunners() {
        Action.this.addRunnersOnce();
        action.addRunnersOnce();
      }
    };
  }

  static void main() {
    sequence(noAction, noAction).show();
  }
}
