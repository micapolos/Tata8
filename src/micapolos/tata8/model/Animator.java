package micapolos.tata8.model;

import micapolos.tata8.Position;
import micapolos.tata8.Speed;

import static micapolos.tata8.model.Clip.animated;

public interface Animator {
  /** Advances animation, and return 0 if animation has not ended, or the number of seconds after animation ended. */
  float advance(float seconds);

  Animator EMPTY = seconds -> seconds;

  default Clip clip() {
    return animated(this);
  }

  static Animator movingX(Position position, float speed) {
    return seconds -> position.x += speed * seconds;
  }

  static Animator movingY(Position position, float speed) {
    return seconds -> position.y += speed * seconds;
  }

  static Animator moving(Position position, float speedX, float speedY) {
    return seconds -> {
      position.x += speedX * seconds;
      position.y += speedX * seconds;
      return seconds;
    };
  }

  static Animator moving(Position position, Speed speed) {
    return moving(position, speed.x, speed.y);
  }
}
