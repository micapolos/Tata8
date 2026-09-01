package micapolos.tata8.model;

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
    Key.Z.pressedSpan().show();
  }
}
