package micapolos.tata8.model.action;

import micapolos.tata8.model.Action;
import micapolos.tata8.model.DoubleValue;

import static micapolos.tata8.model.Action.action;

public final class NumberAction {
  public static Action set(DoubleValue n, double d) {
    return action(() -> n.setImmediately(d));
  }

  public static Action set(DoubleValue n, DoubleValue n2) {
    return action(() -> n.setImmediately(n2));
  }

  public static Action add(DoubleValue n, double d) {
    return action(() -> n.setImmediately(n.get() + d));
  }

  public static Action add(DoubleValue n, DoubleValue n2) {
    return action(() -> n.setImmediately(n.get() + n2.get()));
  }

  public static Action subtract(DoubleValue n, double d) {
    return action(() -> n.setImmediately(n.get() - d));
  }

  public static Action subtract(DoubleValue n, DoubleValue n2) {
    return action(() -> n.setImmediately(n.get() - n2.get()));
  }

  public static Action multiply(DoubleValue n, double d) {
    return action(() -> n.setImmediately(n.get() * d));
  }

  public static Action multiply(DoubleValue n, DoubleValue n2) {
    return action(() -> n.setImmediately(n.get() * n2.get()));
  }

  public static Action negate(DoubleValue n) {
    return action(() -> n.setImmediately(-n.get()));
  }

  private NumberAction() {}
}
