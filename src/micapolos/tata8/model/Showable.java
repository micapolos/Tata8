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
        float advance(float seconds) {
          log(this);
          return 0;
        }
      }
    );
    Game.show();
  }
}
