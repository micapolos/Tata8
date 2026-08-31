package micapolos.tata8.model;

import static micapolos.tata8.model.Bool.bool;
import static micapolos.tata8.model.Clip.animated;

public final class Span implements Showable {
  final Bool previous = Bool.variable();
  final Bool current;
  public final Bool isActive;
  public final Event start;
  public final Event end;

  Span(Bool isActive) {
    current = isActive;
    this.isActive = bool(isActive::get);
    start = () -> !previous.get() && current.get();
    end = () -> previous.get() && !current.get();
  }

  public static Span span(Bool bool) {
    return new Span(bool);
  }

  public Clip clip() {
    return animated(_ -> previous.setImmediately(current.get()));
  }

  @Override
  public String toString() {
    return String.format("span(%s, %s, %s)",
      isActive.get() ? "in" : "out",
      start.didHappen() ? "start" : "-",
      end.didHappen() ? "end" : "-");
  }

  @Override
  public void show() {
    clip().showWith(this);
  }

  static void main() {
    Key.Z.pressedSpan().show();
  }
}
