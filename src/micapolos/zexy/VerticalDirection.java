package micapolos.zexy;

import static micapolos.zexy.Animation.*;
import static micapolos.zexy.On.*;
import static micapolos.zexy.Value.*;

public enum VerticalDirection implements Showable {
  UP, DOWN;

  public static Value<VerticalDirection> fromSpans(Span upSpan, Span downSpan) {
    return value(direction ->
      select(
        on(upSpan.start).lets(direction.set(VerticalDirection.UP)),
        on(upSpan.end).lets(direction.set(downSpan.isInside.ifTrueValue(VerticalDirection.DOWN).orElseValue(null))),
        on(downSpan.start).lets(direction.set(VerticalDirection.DOWN)),
        on(downSpan.end).lets(direction.set(upSpan.isInside.ifTrueValue(VerticalDirection.UP).orElseValue(null)))));
  }

  static void main() {
    fromSpans(Key.UP.pressedSpan(), Key.DOWN.pressedSpan()).show();
  }
}
