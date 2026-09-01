package micapolos.tata8.model;

import static micapolos.tata8.Game.log;

public interface Showable {
  default void show() {
    Game.add(
      new Clip() {
        @Override
        void start() {

        }

        @Override
        float step(float seconds) {
          log(Showable.this);
          return 0;
        }
      }
    );
    Game.show();
  }

  default <T> T showing() {
    show();
    //noinspection unchecked
    return (T) this;
  }
}
