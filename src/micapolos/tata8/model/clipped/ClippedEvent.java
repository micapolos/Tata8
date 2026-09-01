package micapolos.tata8.model.clipped;

import micapolos.tata8.model.BooleanValue;
import micapolos.tata8.model.Clipped;
import micapolos.tata8.model.Event;

public final class ClippedEvent {
  public static Clipped<Event> or(Clipped<Event> a, Clipped<Event> b) {
    return a.update(b, Event::or);
  }

  public static Clipped<Event> and(Clipped<Event> a, Clipped<BooleanValue> b) {
    return a.map(b, Event::and);
  }

  private ClippedEvent() {}
}
