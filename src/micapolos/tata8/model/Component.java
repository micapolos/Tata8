package micapolos.tata8.model;

public class Component implements Showable {
  boolean didAddClips;

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
