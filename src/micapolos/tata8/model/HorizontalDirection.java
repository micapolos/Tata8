package micapolos.tata8.model;

import static micapolos.tata8.model.Clip.on;
import static micapolos.tata8.model.Clip.select;

public enum HorizontalDirection implements Showable {
  LEFT, RIGHT;

  public static Value<HorizontalDirection> fromSpans(Span leftSpan, Span rightSpan) {
    Value<HorizontalDirection> direction = Value.newVariable();

    return
      direction
        .with(
          select(
            on(leftSpan.start, direction.set(HorizontalDirection.LEFT)),
            on(leftSpan.end, direction.set(rightSpan.isInside.selectValue(HorizontalDirection.RIGHT, null))),
            on(rightSpan.start, direction.set(HorizontalDirection.RIGHT)),
            on(rightSpan.end, direction.set(leftSpan.isInside.selectValue(HorizontalDirection.LEFT, null)))));
  }

  static void main() {
    fromSpans(Key.LEFT.pressedSpan(), Key.RIGHT.pressedSpan()).show();
  }
}
