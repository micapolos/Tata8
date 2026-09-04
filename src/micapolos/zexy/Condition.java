package micapolos.zexy;

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
}
