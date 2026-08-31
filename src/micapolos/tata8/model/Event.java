package micapolos.tata8.model;

import micapolos.tata8.Color;
import micapolos.tata8.Game;

import java.util.function.BooleanSupplier;

public interface Event extends Showable {
  boolean didHappen();

  static Event when(BooleanSupplier supplier) {
    return supplier::getAsBoolean;
  }

  default Event and(Bool condition) {
    return () -> didHappen() && condition.get();
  }

  static Event any(Event... events) {
    return () -> {
      boolean any = false;
      for (Event event : events) {
        any |= event.didHappen();
      }
      return any;
    };
  }

  @Override
  default void show() {
    Game.onUpdate = () -> Game.background.color = didHappen() ? Color.WHITE : Color.TRANSPARENT;
    Game.start();
  }

  static void main() {
    Key.RIGHT.pressed.show();
  }
}
