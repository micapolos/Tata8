package micapolos.tata8.model.live;

import micapolos.tata8.model.Boolean;
import micapolos.tata8.model.Live;
import micapolos.tata8.model.Event;

public final class LiveEvents {
  private LiveEvents() {}

  public static Live<Event> or(Live<Event> a, Live<Event> b) {
    return a.update(b, Event::or);
  }

  public static Live<Event> and(Live<Event> a, Live<Boolean> b) {
    return a.map(b, Event::and);
  }
}
