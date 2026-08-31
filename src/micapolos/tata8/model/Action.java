package micapolos.tata8.model;

import micapolos.tata8.Image;

public interface Action {
  void execute();

  Action EMPTY = () -> {};

  static Action action(Runnable runnable) {
    return runnable::run;
  }

  static Action set(micapolos.tata8.Sprite sprite, Image image) {
    return () -> sprite.image = image;
  }

  static Action setX(micapolos.tata8.Position position, float x) {
    return () -> position.x = x;
  }

  static Action setY(micapolos.tata8.Position position, float y) {
    return () -> position.y = y;
  }
  
  static Action sequence(Action... actions) {
    return () -> {
      for (Action action : actions) {
        action.execute();
      }
    };
  }

  default Action then(Action action) {
    return () -> {
      Action.this.execute();
      action.execute();
    };
  }
}
