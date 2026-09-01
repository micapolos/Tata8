package micapolos.tata8.model.action;

import micapolos.tata8.model.Action;
import micapolos.tata8.model.Number;

import static micapolos.tata8.model.Action.action;

public final class DoubleValueActions {
  public static Action set(Number n, double d) {
    return action(() -> n.setImmediately(d));
  }

  public static Action set(Number n, Number n2) {
    return action(() -> n.setImmediately(n2));
  }

  public static Action add(Number n, double d) {
    return action(() -> n.setImmediately(n.get() + d));
  }

  public static Action add(Number n, Number n2) {
    return action(() -> n.setImmediately(n.get() + n2.get()));
  }

  public static Action subtract(Number n, double d) {
    return action(() -> n.setImmediately(n.get() - d));
  }

  public static Action subtract(Number n, Number n2) {
    return action(() -> n.setImmediately(n.get() - n2.get()));
  }

  public static Action multiply(Number n, double d) {
    return action(() -> n.setImmediately(n.get() * d));
  }

  public static Action multiply(Number n, Number n2) {
    return action(() -> n.setImmediately(n.get() * n2.get()));
  }

  public static Action negate(Number n) {
    return action(() -> n.setImmediately(-n.get()));
  }

  private DoubleValueActions() {}
}
