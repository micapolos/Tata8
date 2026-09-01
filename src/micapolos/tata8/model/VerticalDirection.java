package micapolos.tata8.model;

import static micapolos.tata8.model.Clip.on;
import static micapolos.tata8.model.Clip.select;

public enum VerticalDirection implements Showable {
  UP, DOWN;

  public static Value<VerticalDirection> fromSpans(Span upSpan, Span downSpan) {
    var direction = Value.<VerticalDirection>newVariable();
    return
      direction.toValue().with(
        select(
          on(upSpan.start, direction.set(VerticalDirection.UP)),
          on(upSpan.end, direction.set(downSpan.isInside.select(VerticalDirection.DOWN, null))),
          on(downSpan.start, direction.set(VerticalDirection.DOWN)),
          on(downSpan.end, direction.set(upSpan.isInside.select(VerticalDirection.UP, null)))));
  }

  static void main() {
    fromSpans(Key.UP.pressedSpan(), Key.DOWN.pressedSpan()).show();
  }
}
