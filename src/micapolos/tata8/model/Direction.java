package micapolos.tata8.model;

import static micapolos.tata8.model.Clip.parallel;
import static micapolos.tata8.model.Clipped.clipped;
import static micapolos.tata8.model.Value.value;

public enum Direction {
  UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT;

  public static Direction orNullFrom(
    VerticalDirection verticalDirectionOrNull,
    HorizontalDirection horizontalDirectionOrNull)
  {
    if (verticalDirectionOrNull == VerticalDirection.UP) {
      if (horizontalDirectionOrNull == HorizontalDirection.LEFT) {
        return Direction.UP_LEFT;
      } else if (horizontalDirectionOrNull == HorizontalDirection.RIGHT) {
        return Direction.UP_RIGHT;
      } else {
        return Direction.UP;
      }
    } else if (verticalDirectionOrNull == VerticalDirection.DOWN) {
      if (horizontalDirectionOrNull == HorizontalDirection.LEFT) {
        return Direction.DOWN_LEFT;
      } else if (horizontalDirectionOrNull == HorizontalDirection.RIGHT) {
        return Direction.DOWN_RIGHT;
      } else {
        return Direction.DOWN;
      }
    } else {
      if (horizontalDirectionOrNull == HorizontalDirection.LEFT) {
        return Direction.LEFT;
      } else if (horizontalDirectionOrNull == HorizontalDirection.RIGHT) {
        return Direction.RIGHT;
      } else {
        return null;
      }
    }
  }

  public static Clipped<Value<Direction>> fromSpans(
    Span leftPressedSpan,
    Span rightPressedSpan,
    Span upPressedSpan,
    Span downPressedSpan) {
    var verticalDirection = VerticalDirection.fromSpans(upPressedSpan, downPressedSpan);
    var horizontalDirection = HorizontalDirection.fromSpans(leftPressedSpan, rightPressedSpan);
    return clipped(
      value(() -> Direction.orNullFrom(verticalDirection.value.get(), horizontalDirection.value.get())),
      parallel(
        verticalDirection.clip,
        horizontalDirection.clip));
  }

  static void main() {
    fromSpans(Key.LEFT.pressedSpan(), Key.RIGHT.pressedSpan(), Key.UP.pressedSpan(), Key.DOWN.pressedSpan()).show();
  }
}
