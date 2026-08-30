package micapolos.tata8.model;

import static micapolos.tata8.model.Game.add;
import static micapolos.tata8.model.Game.start;

public abstract class Component implements Showable {
  void advance(float seconds) {}

  @Override
  public final void show() {
    add(() -> advance(1/60f));
    start();
  }
}
