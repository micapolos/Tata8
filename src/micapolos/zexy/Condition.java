package micapolos.zexy;

import static micapolos.zexy.Animation.*;
import static micapolos.zexy.Number.*;

public final class Condition {
  final Boolean bool;

  Condition(Boolean bool) {
    this.bool = bool;
  }

  public static Condition when(Boolean bool) {
    return new Condition(bool);
  }

  public ConditionalActivity keep(Activity activity) {
    return new ConditionalActivity(bool, activity);
  }

  public Number keepAdding(double d) {
    return keepAdding(number(d));
  }

  public Number keepAdding(Number number) {
    return new Number(noAnimation) {
      @Override
      void addRunners() {
        bool.addRunnersOnce();
        number.addRunnersOnce();

        Game.add(new Runner() {
          @Override
          public void init() {
            currentValue = 0;
          }

          @Override
          public void update(float seconds) {
            if (bool.get()) {
              currentValue += seconds * number.get();
            }
          }
        });
      }
    };
  }

}
