package micapolos.leo;

import static micapolos.leo.Boolean.bool;
import static micapolos.leo.Clip.*;
import static micapolos.leo.Flip.flip;
import static micapolos.leo.Image.image;
import static micapolos.leo.Number.number;
import static micapolos.leo.Position.position;
import static micapolos.leo.Sprite.newSprite;

public final class Span extends Component {
  public final Boolean isInside;
  public final Event start;
  public final Event end;

  public Span(Boolean isInside, Event start, Event end) {
    this.isInside = isInside;
    this.start = start;
    this.end = end;
  }

  @Override
  void addClips() {
    isInside.maybeAddClips();
    start.maybeAddClips();
    end.maybeAddClips();
  }

  public static Span span(Boolean isInside, Event enter, Event exit) {
    return new Span(isInside, enter, exit);
  }

  @Override
  public String toString() {
    return String.format("span(%s, %s, %s)",
      isInside.get() ? "inside" : "outside",
      start.occurs() ? "start" : "-",
      end.occurs() ? "end" : "-");
  }

  static void main() {
    var span = Key.Z.pressedSpan();
    var x = Number.newNumber();
    var image = image(Span.class, "depressedChicken.png").sliceVertically(8).get(0);
    var sprite = newSprite()
      .with(image)
      .with(position(x, span.isInside.select(number(100), number(200))));
    sprite
      .with(
        parallel(
          select(
            on(span.start, x.add(100)),
            on(span.end, x.add(-100)))))
      .show();
  }
}
