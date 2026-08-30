package micapolos.tata8;

public interface Action {
  void execute();

  static Action set(Sprite sprite, Image image) {
    return () -> sprite.image = image;
  }
}
