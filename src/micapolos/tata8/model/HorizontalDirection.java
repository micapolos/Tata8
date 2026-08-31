package micapolos.tata8.model;

import static micapolos.tata8.model.Clip.on;
import static micapolos.tata8.model.Clip.select;
import static micapolos.tata8.model.Clipped.clipped;

public enum HorizontalDirection {
  LEFT, RIGHT;

  public static Clipped<Value<HorizontalDirection>> fromSpans(Span leftSpan, Span rightSpan) {
    Value<HorizontalDirection> direction = Value.variable();
    return clipped(
      direction.toValue(),
      select(
        on(leftSpan.start, direction.set(HorizontalDirection.LEFT)),
        on(leftSpan.end, direction.set(rightSpan.isInside.select(HorizontalDirection.RIGHT, null))),
        on(rightSpan.start, direction.set(HorizontalDirection.RIGHT)),
        on(rightSpan.end, direction.set(leftSpan.isInside.select(HorizontalDirection.LEFT, null)))));
  }

  static void main() {
    fromSpans(Key.LEFT.pressedSpan(), Key.RIGHT.pressedSpan()).show();
  }
}
