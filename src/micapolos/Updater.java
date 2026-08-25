package micapolos;

public interface Updater {
  Updater EMPTY = () -> {};

  void update();
}
