package micapolos.zexy;

import static micapolos.Leo.*;

public final class ConditionalActivity {
  final Boolean condition;
  final Activity activity;

  ConditionalActivity(Boolean condition, Activity activity) {
    this.condition = condition;
    this.activity = activity;
  }

  @Override
  public String toString() {
    return leo("when", condition, leo("keep", activity));
  }
}
