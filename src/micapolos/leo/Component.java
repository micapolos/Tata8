package micapolos.leo;

public class Component implements Showable {
  public final Clip clip;
  boolean didAddClips;

  Component() {
    this(Clip.emptyClip);
  }

  Component(Clip clip) {
    this.clip = clip;
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
      Game.add(clip);
      didAddClips = true;
    }
  }
}
