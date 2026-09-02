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

  public Animation.EventOption start(Animation animation) {
    return Animation.onStart(event, animation);
  }

  public Animation.EventOption keep(Activity activity) {
    return Animation.onStart(event, noAction.thenKeep(activity));
  }

  public Animation.EventOption execute(Action action) {
    return Animation.onExecute(event, action);
  }
}
