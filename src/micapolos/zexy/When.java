package micapolos.zexy;

import static micapolos.zexy.Action.*;

public final class When {
  final Boolean bool;

  When(Boolean bool) {
    this.bool = bool;
  }

  public static When when(Boolean bool) {
    return new When(bool);
  }

  public Animation.ConditionalActivity keep(Activity activity) {
    return Animation.when(bool, noAction.thenKeep(activity));
  }
}
