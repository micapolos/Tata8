package micapolos.zexy;

import static micapolos.zexy.Action.*;
import static micapolos.zexy.Animation.*;
import static micapolos.zexy.Number.*;

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

  public Number startAdding(double d) {
    return startAdding(number(d));
  }

  public Number startAdding(Number number) {
    return new Number(noAnimation) {
      @Override
      void addRunners() {
        event.addRunnersOnce();
        number.addRunnersOnce();

        Game.add(new Runner() {
          @Override
          public void init() {
            currentValue = 0;
          }

          @Override
          public void update(float seconds) {
            if (event.occurs()) {
              currentValue = 0;
            }
            currentValue += seconds * number.get();
          }
        });
      }
    };
  }
}
