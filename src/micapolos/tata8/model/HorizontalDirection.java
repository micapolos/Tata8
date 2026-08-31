package micapolos.tata8.model;

import static micapolos.tata8.model.Clip.on;
import static micapolos.tata8.model.Clip.select;

public enum HorizontalDirection {
  LEFT, RIGHT;

  public static Clipped<Value<HorizontalDirection>> fromSpans(Span leftSpan, Span rightSpan) {
    Value<HorizontalDirection> direction = Value.variable();
    return Clipped.clipped(
      direction.toValue(),
      select(
        on(Key.LEFT.press, direction.set(HorizontalDirection.LEFT)),
        on(Key.LEFT.release, direction.set(Key.RIGHT.isPressed.select(HorizontalDirection.RIGHT, HorizontalDirection.LEFT))),
        on(Key.RIGHT.press, direction.set(HorizontalDirection.RIGHT)),
        on(Key.RIGHT.release, direction.set(Key.LEFT.isPressed.select(HorizontalDirection.LEFT, HorizontalDirection.RIGHT)))));
  }
}
