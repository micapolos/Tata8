package micapolos.tata8.model;

import static micapolos.tata8.model.Clip.*;
import static micapolos.tata8.model.Number.*;

public final class Depth extends ValueComponent {
  public final Number number;

  Depth(Clip clip, boolean isVariable, Number number) {
    super(clip, isVariable);
    this.number = number;
  }

  public static Depth depth(double d) {
    return depth(number(d));
  }

  public static Depth depth(Number number) {
    return new Depth(emptyClip, false, number);
  }

  public Depth with(Clip clip) {
    checkVariable();
    return new Depth(clip, false, number);
  }

  @Override
  void addClips() {
    number.maybeAddClips();
  }

  @Override
  public String toString() {
    return String.format("depth(%s)", number);
  }

  static void main() {
    depth(seconds).show();
  }
}
