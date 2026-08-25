package micapolos.tata8;

public interface Updater {
  Updater EMPTY = () -> {};

  void update();
}
