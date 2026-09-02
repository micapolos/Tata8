package micapolos.zexy;

import static micapolos.tata8.Game.*;

public interface Showable {
  default void show() {
    Game.add(
      new Clip() {
        @Override
        void start() {

        }

        @Override
        float step(float seconds) {
          if (Showable.this instanceof Drawable drawable) {
            background.canvas.clear();
            drawable.drawOn(background.canvas);
          }
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
