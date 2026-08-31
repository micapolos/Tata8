package micapolos.tata8.model;

import micapolos.tata8.Position;
import micapolos.tata8.Speed;

import static micapolos.tata8.model.Clip.animated;

public interface Animation {
  void update(float seconds);

  Animation EMPTY = _ -> {};

  default Clip clip() {
    return animated(this);
  }

  static Animation movingX(Position position, float speed) {
    return seconds -> position.x += speed * seconds;
  }

  static Animation movingY(Position position, float speed) {
    return seconds -> position.y += speed * seconds;
  }

  static Animation moving(Position position, float speedX, float speedY) {
    return seconds -> {
      position.x += speedX * seconds;
      position.y += speedX * seconds;
    };
  }

  static Animation moving(Position position, Speed speed) {
    return moving(position, speed.x, speed.y);
  }
}
