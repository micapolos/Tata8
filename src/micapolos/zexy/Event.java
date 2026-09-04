package micapolos.zexy;

import micapolos.Leo;

import java.util.function.BooleanSupplier;

import static micapolos.zexy.Animation.*;

public class Event extends ValueComponent {
  BooleanSupplier occursSupplier;
  boolean defaultOccurs;

  Event() {
    this(noAnimation, null, false);
  }

  Event(BooleanSupplier occursSupplier) {
    this(noAnimation, occursSupplier, false);
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

  public Boolean isOcurring() {
    return new Boolean(this::occurs) {
      @Override
      void addRunners() {
        Event.this.addRunnersOnce();
      }
    };
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
    return new Event(() -> occurs() || event.occurs()) {
      @Override
      void addRunners() {
        Event.this.addRunnersOnce();
        event.addRunnersOnce();
      }
    };
  }

  public Event and(Boolean bool) {
    return new Event(() -> occurs() && bool.get()) {
      @Override
      void addRunners() {
        Event.this.addRunnersOnce();
        bool.addRunnersOnce();
      }
    };
  }

  public static Event any(Event... events) {
    return new Event(() -> {
      boolean any = false;
      for (Event event : events) {
        any |= event.occurs();
      }
      return any;
    }) {
      @Override
      void addRunners() {
        for (Event event : events) {
          event.addRunnersOnce();
        }
      }
    };
  }

  @Override
  public String toString() {
    return Leo.leo("event", occurs());
  }

  @Override
  public void show() {
    isOcurring().show();
  }

  static void main() {
    Key.Z.press.show();
  }
}
