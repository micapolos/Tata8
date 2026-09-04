package micapolos.zexy;

import micapolos.tata8.Canvas;

import static micapolos.tata8.Game.*;

public interface Drawable extends Showable {
  void drawOn(Canvas canvas);

  @Override
  default void show() {
    showLogging();
  }

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
            micapolos.tata8.Game.log(object);
          }
          return 0;
        }
      }
    );
    Game.show();
  }
}
