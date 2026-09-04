package micapolos.zexy;

import static micapolos.zexy.Animation.*;
import static micapolos.zexy.On.*;
import static micapolos.zexy.Value.*;

public enum HorizontalDirection implements Showable {
  LEFT, RIGHT;

  public static Value<HorizontalDirection> fromSpans(Span leftSpan, Span rightSpan) {
    return
      value(direction ->
        select(
          on(leftSpan.start).lets(direction.set(HorizontalDirection.LEFT)),
          on(leftSpan.end).lets(direction.set(rightSpan.isInside.ifTrueValue(HorizontalDirection.RIGHT).orElseValue(null))),
          on(rightSpan.start).lets(direction.set(HorizontalDirection.RIGHT)),
          on(rightSpan.end).lets(direction.set(leftSpan.isInside.ifTrueValue(HorizontalDirection.LEFT).orElseValue(null)))));
  }

  static void main() {
    fromSpans(Key.LEFT.pressedSpan(), Key.RIGHT.pressedSpan()).show();
  }
}
