package micapolos.zexy;

import micapolos.tata8.Game;

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
    if (this instanceof Drawable drawable) {
      drawable.show();
    } else {
      Showable.super.show();
    }
  }

  public Action log() {
    return new Action() {
      @Override
      void execute() {
        IO.println(Component.this);
      }

      @Override
      void addRunners() {
        Component.this.addRunnersOnce();
      }
    };
  }

  public Action log(String label) {
    return new Action() {
      @Override
      void execute() {
        IO.println(String.format("%s: %s", label, Component.this));
      }

      @Override
      void addRunners() {
        Component.this.addRunnersOnce();
      }
    };
  }

  public Animation startLogging() {
    return new Animation() {
      @Override
      void start() {

      }

      @Override
      float step(float seconds) {
        Game.log(Component.this);
        return 0;
      }

      @Override
      void addRunners() {
        Component.this.addRunnersOnce();
      }
    };
  }

  public Animation startLoggingWith(String label) {
    return new Animation() {
      @Override
      void start() {

      }

      @Override
      float step(float seconds) {
        Game.log(label, Component.this);
        return 0;
      }

      @Override
      void addRunners() {
        Component.this.addRunnersOnce();
      }
    };
  }

  public Activity keepLogging() {
    return new Activity() {
      @Override
      void advance(float seconds) {
        Game.log(Component.this);
      }

      @Override
      void addRunners() {
        Component.this.addRunnersOnce();
      }
    };
  }

  public Activity keepLogging(String label) {
    return new Activity() {
      @Override
      void advance(float seconds) {
        Game.log(label, Component.this);
      }

      @Override
      void addRunners() {
        Component.this.addRunnersOnce();
      }
    };
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
