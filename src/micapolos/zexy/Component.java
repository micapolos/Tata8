package micapolos.zexy;

public class Component implements Showable {
  public final Animation animation;
  boolean didAddClips;

  Component() {
    this(Animation.EMPTY_ANIMATION);
  }

  Component(Animation animation) {
    this.animation = animation;
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
      if (animation != null) {
        animation.addRunnersOnce();
      }
      didAddClips = true;
    }
  }
}
