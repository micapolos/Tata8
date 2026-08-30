package micapolos.tata8.model;

import static micapolos.tata8.Game.log;

public interface Showable {
  default void show() {
    Game.add(() -> log(this));
    Game.start();
  }
}
