package micapolos.sad;

import micapolos.tata8.Canvas;
import micapolos.tata8.Game;

import static micapolos.Leo.*;
import static micapolos.sad.Number.*;

public abstract class Drawing extends Component {
  void drawOn(Canvas canvas) {
  }

  public static Drawing fillRect(Number x, Number y, Number width, Number height) {
    return new Drawing() {
      @Override
      void drawOn(Canvas canvas) {
        canvas.fillRect((int) x.get(), (int) y.get(), (int) width.get(), (int) height.get());
      }

      @Override
      void compileDeps(Compiler compiler) {
        compiler.compile(x);
        compiler.compile(y);
        compiler.compile(width);
        compiler.compile(height);
      }

      @Override
      String toLeo() {
        return leo("fill rect", leo("x", x), leo("y", y), leo("width", width), leo("height", height));
      }
    };
  }

  public final void show() {
    Component component = compile();
    component.init();
    component.update();
    Game.onUpdate = () -> {
      Game.background.canvas.clear();
      drawOn(Game.background.canvas);
      component.step(1 / 60f);
      component.update();
    };
    Game.start();
  }

  static void main() {
    fillRect(seconds.times(number(60)), seconds.times(number(30)), number(10), number(10)).show();
  }
}
