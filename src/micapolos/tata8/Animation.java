package micapolos.tata8;

public interface Animation {
  void update(float seconds);

  Animation EMPTY = _ -> {};

  static Animation move(Position position, Speed speed) {
    return seconds -> {
      position.x += speed.x * seconds;
      position.y += speed.y * seconds;
    };
  }
}
