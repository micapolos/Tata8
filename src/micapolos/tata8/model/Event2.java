package micapolos.tata8.model;

import micapolos.tata8.Color;

import java.util.function.BooleanSupplier;

public final class Event2 extends Component {
  BooleanSupplier occursSupplier;
  boolean defaultOccurs;

  Event2(boolean isVariable, BooleanSupplier occursSupplier, boolean defaultOccurs) {
    super(isVariable);
    this.occursSupplier = occursSupplier;
    this.defaultOccurs = defaultOccurs;
  }

  public boolean occurs() {
    BooleanSupplier supplier = this.occursSupplier;
    return supplier != null ? supplier.getAsBoolean() : defaultOccurs;
  }

  public static Event2 event(boolean occurs) {
    return new Event2(false, null, occurs);
  }

  public static Event2 event(Event2 event) {
    return event(event::occurs);
  }

  public static Event2 event(BooleanSupplier occursSupplier) {
    return new Event2(false, occursSupplier, false);
  }

  public static Event2 variable() {
    return variable(event(false));
  }

  public static Event2 variable(Event2 event) {
    return new Event2(true, event.occursSupplier, event.defaultOccurs);
  }

  void occurImmediately() {
    setImmediately(event(true));
  }

  void setImmediately(Event2 event) {
    occursSupplier = event::occurs;
    defaultOccurs = false;
  }

  public void init(Event2 event) {
    init(() -> setImmediately(event));
  }

  public Event2 and(Bool bool) {
    return event(() -> occurs() && bool.get());
  }

  public static Event2 any(Event... events) {
    return event(() -> {
      boolean any = false;
      for (Event event : events) {
        any |= event.didHappen();
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
    Key.RIGHT.press.show();
  }
}
