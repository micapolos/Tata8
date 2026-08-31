package micapolos.tata8.model;

import static micapolos.tata8.Game.log;
import static micapolos.tata8.model.Game.add;

public class Component implements Showable {
  void start() {}

  float advance(float seconds) {
    return 0;
  }

  @Override
  public void show() {
    start();
    add(() -> {
      advance(1/60f);
      log(this);
    });
    Game.show();
  }
}
