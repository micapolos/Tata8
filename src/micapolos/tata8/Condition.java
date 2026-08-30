package micapolos.tata8;

public interface Condition {
  boolean isHappening();

  static Condition all(Condition... conditions) {
    return () -> {
      boolean all = true;
      for (Condition condition : conditions) {
        all = all && condition.isHappening();
      }
      return all;
    };
  }

  static Condition any(Condition... conditions) {
    return () -> {
      boolean any = false;
      for (Condition condition : conditions) {
        any = any || condition.isHappening();
      }
      return any;
    };
  }

  static Condition not(Condition condition) {
    return () -> !condition.isHappening();
  }
}

