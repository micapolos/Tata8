package micapolos.zexy;

import micapolos.tata8.Canvas;

import static micapolos.tata8.Game.*;

public interface Drawable {
  void drawOn(Canvas canvas);

  default void showLogging(Object... debugObjects) {
    Game.add(
      new Animation() {
        @Override
        void start() {

        }

        @Override
        float step(float seconds) {
          background.canvas.clear();
          drawOn(background.canvas);
          for (Object object : debugObjects) {
            log(object);
          }
          return 0;
        }
      }
    );
    Game.show();
  }
}
