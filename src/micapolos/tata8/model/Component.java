package micapolos.tata8.model;

public class Component implements Showable {
  public final boolean isVariable;
  boolean didAddClips;
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

  void addClips() {}

  @Override
  public void show() {
    maybeAddClips();
    Showable.super.show();
  }

  final void maybeAddClips() {
    if (!didAddClips) {
      addClips();
      didAddClips = true;
    }
  }
}
