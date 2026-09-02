package micapolos.zexy;

import static micapolos.zexy.Action.*;
import static micapolos.zexy.Animation.*;

public final class When {
  final Boolean bool;

  When(Boolean bool) {
    this.bool = bool;
  }

  public static When when(Boolean bool) {
    return new When(bool);
  }

  public Animation.ConditionalActivity keep(Activity activity) {
    return whenKeep(bool, noAction.thenKeep(activity));
  }
}
