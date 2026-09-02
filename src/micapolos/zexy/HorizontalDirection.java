package micapolos.zexy;

import static micapolos.zexy.Animation.select;

public enum HorizontalDirection implements Showable {
  LEFT, RIGHT;

  public static Value<HorizontalDirection> fromSpans(Span leftSpan, Span rightSpan) {
    Value<HorizontalDirection> direction = Value.newVariable();

    return
      direction
        .with(
          select(
            Animation.onExecute(leftSpan.start, direction.set(HorizontalDirection.LEFT)),
            Animation.onExecute(leftSpan.end, direction.set(rightSpan.isInside.selectValue(HorizontalDirection.RIGHT, null))),
            Animation.onExecute(rightSpan.start, direction.set(HorizontalDirection.RIGHT)),
            Animation.onExecute(rightSpan.end, direction.set(leftSpan.isInside.selectValue(HorizontalDirection.LEFT, null)))));
  }

  static void main() {
    fromSpans(Key.LEFT.pressedSpan(), Key.RIGHT.pressedSpan()).show();
  }
}
