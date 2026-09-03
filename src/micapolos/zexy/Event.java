package micapolos.zexy;

import micapolos.Leo;
import micapolos.tata8.Color;

import java.util.function.BooleanSupplier;

import static micapolos.zexy.Animation.*;

public class Event extends ValueComponent {
  BooleanSupplier occursSupplier;
  boolean defaultOccurs;

  Event() {
    this(noAnimation, null, false);
  }

  Event(Animation animation, BooleanSupplier occursSupplier, boolean defaultOccurs) {
    this.animation = animation;
    this.occursSupplier = occursSupplier;
    this.defaultOccurs = defaultOccurs;
  }

  public static final Event noEvent = event(false);

  boolean occurs() {
    BooleanSupplier supplier = this.occursSupplier;
    return supplier != null ? supplier.getAsBoolean() : defaultOccurs;
  }

  static Event event(boolean occurs) {
    return new Event(noAnimation, null, occurs);
  }

  public static Event event(Event event) {
    return new Event(noAnimation, event::occurs, false) {
      @Override
      void addRunners() {
        event.addRunnersOnce();
      }
    };
  }

  static Event event(BooleanSupplier occursSupplier) {
    return new Event(noAnimation, occursSupplier, false);
  }

  public static Event newEvent() {
    return new Event(null, null, false);
  }

  void occurImmediately() {
    setImmediately(event(true));
  }

  void setImmediately(Event event) {
    occursSupplier = event::occurs;
    defaultOccurs = false;
  }

  public Event or(Event event) {
    return event(() -> occurs() && event.occurs());
  }

  public Event and(Boolean aBoolean) {
    return event(() -> occurs() && aBoolean.get());
  }

  public static Event any(Event... events) {
    return event(() -> {
      boolean any = false;
      for (Event event : events) {
        any |= event.occurs();
      }
      return any;
    });
  }

  @Override
  public String toString() {
    return Leo.leo("event", occurs());
  }

  @Override
  public void show() {
    micapolos.tata8.Game.onUpdate = () -> micapolos.tata8.Game.background.color = occurs() ? micapolos.tata8.Color.WHITE : Color.TRANSPARENT;
    micapolos.tata8.Game.start();
  }

  static void main() {
    Key.RIGHT.press.and(Key.Z.isPressed).show();
  }
}
