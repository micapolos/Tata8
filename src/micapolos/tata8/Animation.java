package micapolos.tata8;

public interface Animation {
  void update(float seconds);

  Animation EMPTY = _ -> {};

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
