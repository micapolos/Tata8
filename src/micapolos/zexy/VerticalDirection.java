package micapolos.zexy;

import static micapolos.zexy.Animation.*;
import static micapolos.zexy.On.*;
import static micapolos.zexy.Value.*;

public enum VerticalDirection implements Showable {
  UP, DOWN;

  public static Value<VerticalDirection> fromSpans(Span upSpan, Span downSpan) {
    return value(direction ->
      select(
        on(upSpan.start).execute(direction.set(VerticalDirection.UP)),
        on(upSpan.end).execute(direction.set(downSpan.isInside.selectValue(VerticalDirection.DOWN, null))),
        on(downSpan.start).execute(direction.set(VerticalDirection.DOWN)),
        on(downSpan.end).execute(direction.set(upSpan.isInside.selectValue(VerticalDirection.UP, null)))));
  }

  static void main() {
    fromSpans(Key.UP.pressedSpan(), Key.DOWN.pressedSpan()).show();
  }
}
