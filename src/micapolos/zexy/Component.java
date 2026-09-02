package micapolos.zexy;

public class Component implements Showable {
  public final Clip clip;
  boolean didAddClips;

  Component() {
    this(Clip.emptyClip);
  }

  Component(Clip clip) {
    this.clip = clip;
  }

  void addRunners() {}

  @Override
  public void show() {
    addRunnersOnce();
    Showable.super.show();
  }

  final void addRunnersOnce() {
    if (!didAddClips) {
      addRunners();
      if (clip != null) {
        Game.add(clip);
      }
      didAddClips = true;
    }
  }
}
