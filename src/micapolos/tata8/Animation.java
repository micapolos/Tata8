package micapolos.tata8;

public interface Animation {
  void update(float seconds);

  Animation EMPTY = _ -> {};

  static Animation moveX(Position position, float speed) {
    return seconds -> position.x += speed * seconds;
  }

  static Animation moveY(Position position, float speed) {
    return seconds -> position.y += speed * seconds;
  }

  static Animation move(Position position, float speedX, float speedY) {
    return seconds -> {
      position.x += speedX * seconds;
      position.y += speedX * seconds;
    };
  }

  static Animation move(Position position, Speed speed) {
    return move(position, speed.x, speed.y);
  }
}
