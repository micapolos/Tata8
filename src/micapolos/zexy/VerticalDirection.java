package micapolos.zexy;

import static micapolos.zexy.Animation.select;

public enum VerticalDirection implements Showable {
  UP, DOWN;

  public static Value<VerticalDirection> fromSpans(Span upSpan, Span downSpan) {
    var direction = Value.<VerticalDirection>newVariable();
    return
      direction.readonly().with(
        select(
          Animation.onExecute(upSpan.start, direction.set(VerticalDirection.UP)),
          Animation.onExecute(upSpan.end, direction.set(downSpan.isInside.selectValue(VerticalDirection.DOWN, null))),
          Animation.onExecute(downSpan.start, direction.set(VerticalDirection.DOWN)),
          Animation.onExecute(downSpan.end, direction.set(upSpan.isInside.selectValue(VerticalDirection.UP, null)))));
  }

  static void main() {
    fromSpans(Key.UP.pressedSpan(), Key.DOWN.pressedSpan()).show();
  }
}
