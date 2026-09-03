package micapolos.zexy;

import static micapolos.Leo.*;

public final class ConditionalActivity {
  final Boolean condition;
  final Animation animation;

  ConditionalActivity(Boolean condition, Animation animation) {
    this.condition = condition;
    this.animation = animation;
  }

  @Override
  public String toString() {
    return leo("when", condition, leo("execute", animation));
  }
}
