package micapolos.tata8;

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

  static Action sequence(Action... actions) {
    return () -> {
      for (Action action : actions) {
        action.execute();
      }
    };
  }
}
