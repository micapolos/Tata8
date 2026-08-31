package micapolos.tata8.model;

import micapolos.tata8.Position;
import micapolos.tata8.Speed;

import static micapolos.tata8.model.Clip.animated;

public interface Stepper {
  /**
   * Performs single step of given duration, return 0 if there are no more steps, or the number of seconds after the step ended.
   */
  float step(float seconds);

  Stepper EMPTY = seconds -> seconds;

  default Clip clip() {
    return animated(this);
  }

  static Stepper movingX(Position position, float speed) {
    return seconds -> position.x += speed * seconds;
  }

  static Stepper movingY(Position position, float speed) {
    return seconds -> position.y += speed * seconds;
  }

  static Stepper moving(Position position, float speedX, float speedY) {
    return seconds -> {
      position.x += speedX * seconds;
      position.y += speedX * seconds;
      return seconds;
    };
  }

  static Stepper moving(Position position, Speed speed) {
    return moving(position, speed.x, speed.y);
  }
}
