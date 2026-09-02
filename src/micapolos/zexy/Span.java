package micapolos.zexy;

import static micapolos.zexy.Animation.*;
import static micapolos.zexy.Image.*;
import static micapolos.zexy.Number.*;
import static micapolos.zexy.Position.*;
import static micapolos.zexy.Sprite.*;
import static micapolos.Leo.*;

public final class Span extends Component {
  public final Boolean isInside;
  public final Event start;
  public final Event end;

  public Span(Boolean isInside, Event start, Event end) {
    this.isInside = isInside;
    this.start = start;
    this.end = end;
  }

  @Override
  void addRunners() {
    isInside.addRunnersOnce();
    start.addRunnersOnce();
    end.addRunnersOnce();
  }

  public static Span span(Boolean isInside, Event enter, Event exit) {
    return new Span(isInside, enter, exit);
  }

  @Override
  public String toString() {
    return leo("span", leo("isInside", isInside), leo("start", start), leo("end", end));
  }

  static void main() {
    var span = Key.Z.pressedSpan();
    var x = Number.newNumber();
    var image = image(Span.class, "depressedChicken.png").sliceVertically(8).get(0);
    var sprite = newSprite()
      .with(image)
      .with(position(x, span.isInside.select(number(100), number(200))));
    sprite
      .with(
        parallel(
          select(
            onExecute(span.start, x.add(100)),
            onExecute(span.end, x.add(-100)))))
      .show();
  }
}
