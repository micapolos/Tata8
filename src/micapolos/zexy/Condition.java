package micapolos.zexy;

import static micapolos.zexy.Action.*;
import static micapolos.zexy.Animation.*;

public final class Condition {
  final Boolean bool;

  Condition(Boolean bool) {
    this.bool = bool;
  }

  public static Condition when(Boolean bool) {
    return new Condition(bool);
  }

  public ConditionalActivity keep(Activity activity) {
    return whenKeep(bool, noAction.thenKeep(activity));
  }
}
