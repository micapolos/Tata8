package micapolos.tata8.model;

import static micapolos.tata8.model.Boolean.bool;
import static micapolos.tata8.model.Clip.*;
import static micapolos.tata8.model.Flip.flip;
import static micapolos.tata8.model.Image.image;
import static micapolos.tata8.model.Number.number;
import static micapolos.tata8.model.Position.position;
import static micapolos.tata8.model.Sprite.newSprite;

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
    var x = Number.newVariable();
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
