package micapolos.zexy;

import static micapolos.Leo.*;

public final class EventOption {
  final Event event;
  final Animation animation;

  EventOption(Event event, Animation animation) {
    this.event = event;
    this.animation = animation;
  }

  @Override
  public String toString() {
    return leo("on", event, leo("execute", animation));
  }
}
