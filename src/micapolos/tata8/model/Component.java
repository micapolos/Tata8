package micapolos.tata8.model;

public class Component implements Showable {
  public final boolean isVariable;
  boolean didAddClips;

  Component() {
    this(false);
  }

  Component(boolean isVariable) {
    this.isVariable = isVariable;
  }

  final void checkVariable() {
    if (!isVariable) {
      throw new IllegalArgumentException("Not a variable");
    }
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
