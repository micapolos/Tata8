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

  public static Value<VerticalDirection> fromSpans(Boolean isUpPressed, Boolean isDownPressed) {
    return value(direction ->
      select(
        on(isUpPressed.changeTo(true)).lets(direction.set(VerticalDirection.UP)),
        on(isUpPressed.changeTo(false)).lets(direction.set(isDownPressed.ifTrueValue(VerticalDirection.DOWN).orElseValue(null))),
        on(isDownPressed.changeTo(true)).lets(direction.set(VerticalDirection.DOWN)),
        on(isDownPressed.changeTo(false)).lets(direction.set(isUpPressed.ifTrueValue(VerticalDirection.UP).orElseValue(null)))));
  }

  static void main() {
    fromSpans(Key.UP.isPressed, Key.DOWN.isPressed).show();
  }
}
