package micapolos.zexy;

import static micapolos.zexy.Action.*;

public final class On {
  final Event event;

  On(Event event) {
    this.event = event;
  }

  public static On on(Event event) {
    return new On(event);
  }

  public EventOption lets(Animation animation) {
    return Animation.onStart(event, animation);
  }

  @Deprecated(forRemoval = true)
  public EventOption start(Animation animation) {
    return Animation.onStart(event, animation);
  }

  public EventOption keep(Activity activity) {
    return Animation.onStart(event, noAction.thenKeep(activity));
  }

  public EventOption lets(Action action) {
    return Animation.onExecute(event, action);
  }

  @Deprecated(forRemoval = true)
  public EventOption execute(Action action) {
    return Animation.onExecute(event, action);
  }
}
