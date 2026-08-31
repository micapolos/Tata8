package micapolos.tata8.model;

import static micapolos.tata8.model.Clip.parallel;
import static micapolos.tata8.model.Clipped.clipped;

public enum Direction {
  UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT;

  public static Clipped<Value<Direction>> fromSpans(Span leftPressedSpan, Span rightPressedSpan, Span upPressedSpan, Span downPressedSpan) {
    var verticalDirection = VerticalDirection.fromSpans(upPressedSpan, downPressedSpan);
    var horizontalDirection = HorizontalDirection.fromSpans(leftPressedSpan, rightPressedSpan);
    return clipped(
      Value.value(() -> {
        VerticalDirection vert = verticalDirection.value.get();
        HorizontalDirection hor = horizontalDirection.value.get();
        if (vert == VerticalDirection.UP) {
          if (hor == HorizontalDirection.LEFT) {
            return Direction.UP_LEFT;
          } else if (hor == HorizontalDirection.RIGHT) {
            return Direction.UP_RIGHT;
          } else {
            return Direction.UP;
          }
        } else if (vert == VerticalDirection.DOWN) {
          if (hor == HorizontalDirection.LEFT) {
            return Direction.DOWN_LEFT;
          } else if (hor == HorizontalDirection.RIGHT) {
            return Direction.DOWN_RIGHT;
          } else {
            return Direction.DOWN;
          }
        } else {
          if (hor == HorizontalDirection.LEFT) {
            return Direction.LEFT;
          } else if (hor == HorizontalDirection.RIGHT) {
            return Direction.RIGHT;
          } else {
            return null;
          }
        }}),
      parallel(
        verticalDirection.clip,
        horizontalDirection.clip));
  }

  static void main() {
    fromSpans(Key.LEFT.pressedSpan(), Key.RIGHT.pressedSpan(), Key.UP.pressedSpan(), Key.DOWN.pressedSpan()).show();
  }
}
