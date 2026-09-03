package micapolos.zexy;

import static micapolos.tata8.Game.*;

public interface Showable {
  default void show() {
    Game.add(
      new Animation() {
        @Override
        void start() {

        }

        @Override
        float step(float seconds) {
          background.canvas.clear();
          if (Showable.this instanceof Drawable drawable) {
            drawable.drawOn(background.canvas);
          } else if (Showable.this instanceof Drawing drawing) {
            drawing.drawOn(background.canvas);
          } else {
            log(Showable.this);
          }
          return 0;
        }
      }
    );
    Game.show();
  }
}
