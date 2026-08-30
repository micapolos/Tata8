package micapolos.tata8.model;

import micapolos.tata8.Image;
import micapolos.tata8.Position;
import micapolos.tata8.Sprite;

public interface Action {
  void execute();

  static Action set(Sprite sprite, Image image) {
    return () -> sprite.image = image;
  }

  static Action setX(Position position, float x) {
    return () -> position.x = x;
  }

  static Action setY(Position position, float y) {
    return () -> position.y = y;
  }

  static Action moveX(Position position, float dx) {
    return () -> position.x += dx;
  }

  static Action moveY(Position position, float dy) {
    return () -> position.y += dy;
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
