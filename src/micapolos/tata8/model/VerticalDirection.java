package micapolos.tata8.model;

import static micapolos.tata8.model.Clip.on;
import static micapolos.tata8.model.Clip.select;
import static micapolos.tata8.model.Clipped.clipped;

public enum VerticalDirection implements Showable {
  UP, DOWN;

  public static Clipped<Value<VerticalDirection>> fromSpans(Span upSpan, Span downSpan) {
    var direction = Value.<VerticalDirection>variable();
    return clipped(
      direction.toValue(),
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
