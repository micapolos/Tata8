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
          on(leftSpan.start).execute(direction.set(HorizontalDirection.LEFT)),
          on(leftSpan.end).execute(direction.set(rightSpan.isInside.selectValue(HorizontalDirection.RIGHT, null))),
          on(rightSpan.start).execute(direction.set(HorizontalDirection.RIGHT)),
          on(rightSpan.end).execute(direction.set(leftSpan.isInside.selectValue(HorizontalDirection.LEFT, null)))));
  }

  static void main() {
    fromSpans(Key.LEFT.pressedSpan(), Key.RIGHT.pressedSpan()).show();
  }
}
