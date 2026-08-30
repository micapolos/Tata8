package micapolos.tata8;

public interface Event {
  boolean didHappen();

  static Event on(Event event) {
    return () -> event.didHappen();
  }
}
