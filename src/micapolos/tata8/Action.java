package micapolos.tata8;

public interface Action {
  void execute();

  static Action action(Action action) {
    return action;
  }
}
