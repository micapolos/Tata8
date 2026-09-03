package micapolos.zexy;

public class Component implements Showable {
  Animation animation;
  boolean didAddClips;

  Component() {}

  Component(Animation animation) {
    this.animation = animation;
  }

  void addRunners() {}

  final void init(Animation animation) {
    checkVariable();
    this.animation = animation;
  }

  public boolean isReadonly() {
    return animation != null;
  }

  final void checkVariable() {
    if (isReadonly()) {
      throw new IllegalArgumentException("Animation already initialized.");
    }
  }

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
