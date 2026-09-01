package micapolos.tata8.model;

import micapolos.tata8.Color;

import java.util.function.BooleanSupplier;

public final class Event extends ValueComponent {
  BooleanSupplier occursSupplier;
  boolean defaultOccurs;

  Event(boolean isVariable, BooleanSupplier occursSupplier, boolean defaultOccurs) {
    super(isVariable);
    this.occursSupplier = occursSupplier;
    this.defaultOccurs = defaultOccurs;
  }

  public boolean occurs() {
    BooleanSupplier supplier = this.occursSupplier;
    return supplier != null ? supplier.getAsBoolean() : defaultOccurs;
  }

  public static Event event(boolean occurs) {
    return new Event(false, null, occurs);
  }

  public static Event event(Event event) {
    return event(event::occurs);
  }

  public static Event event(BooleanSupplier occursSupplier) {
    return new Event(false, occursSupplier, false);
  }

  public static Event newEvent() {
    return newEvent(event(false));
  }

  public static Event newEvent(Event event) {
    return new Event(true, event.occursSupplier, event.defaultOccurs);
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
    return String.valueOf(occurs());
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
