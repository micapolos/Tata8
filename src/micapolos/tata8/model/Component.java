package micapolos.tata8.model;

public class Component implements Showable {
  public final boolean isVariable;
  Action initialize = Action.EMPTY;

  Component(boolean isVariable) {
    this.isVariable = isVariable;
  }

  final void checkVariable() {
    if (!isVariable) {
      throw new IllegalArgumentException("Not a variable");
    }
  }

  final void init(Action initialize) {
    checkVariable();
    initialize.execute();
    this.initialize = initialize;
  }
}
