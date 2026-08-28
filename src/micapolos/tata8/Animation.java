package micapolos.tata8;

public abstract class Animation {
  abstract void start();

  abstract void update(float seconds);

  abstract float remaining();

  public final void update() {
    update(1/60f);
  }

  public boolean didFinish() {
    return remaining() <= 0;
  }

  public static Animation instant() {
    return new Animation() {
      @Override
      void start() {

      }

      @Override
      void update(float seconds) {

      }

      @Override
      float remaining() {
        return 0;
      }
    };
  }

  public static Animation forever() {
    return new Animation() {
      @Override
      void start() {

      }

      @Override
      void update(float seconds) {

      }

      @Override
      float remaining() {
        return Float.POSITIVE_INFINITY;
      }
    };
  }

  public static Animation pause(Duration duration) {
    return new Animation() {
      float doneSeconds;

      @Override
      public void start() {
        doneSeconds = 0;
      }

      @Override
      public void update(float seconds) {
        doneSeconds += seconds;
      }

      @Override
      public float remaining() {
        return duration.seconds - doneSeconds;
      }
    };
  }

  public final Animation onStart(Runnable runnable) {
    return new Animation() {
      @Override
      public void start() {
        runnable.run();
        Animation.this.start();
      }

      @Override
      public void update(float seconds) {
        Animation.this.update(seconds);
      }

      @Override
      public float remaining() {
        return Animation.this.remaining();
      }
    };
  }

  public final Animation then(Animation secondAnimation) {
    return new Animation() {
      boolean isRunningFirst;

      @Override
      void start() {
        Animation.this.start();
        secondAnimation.start();
        isRunningFirst = true;
      }

      @Override
      void update(float seconds) {
        if (isRunningFirst) {
          float remaining = Animation.this.remaining();
          if (seconds < remaining) {
            Animation.this.update(seconds);
          } else {
            Animation.this.update(remaining);
            isRunningFirst = false;
            secondAnimation.update(seconds - remaining);
          }
        } else {
          secondAnimation.update(seconds);
        }
      }

      @Override
      float remaining() {
        return isRunningFirst
            ? Animation.this.remaining() + secondAnimation.remaining()
            : secondAnimation.remaining();
      }
    };
  }

  public static Animation sequence(Animation... animations) {
    Animation animation = instant();
    for (Animation nextAnimation : animations) {
      animation = animation.then(nextAnimation);
    }
    return animation;
  }

  public final Animation repeat(int times) {
    Animation animation = instant();
    for (int i = 0; i < times; i++) {
      animation = animation.then(Animation.this);
    }
    return animation;
  }

  public final Animation loop() {
    return new Animation() {
      @Override
      void start() {
        Animation.this.start();
      }

      @Override
      void update(float seconds) {
        while (true) {
          float remaining = Animation.this.remaining();
          if (seconds < remaining) {
            Animation.this.update(seconds);
            break;
          }
          Animation.this.update(remaining);
          Animation.this.start();
          seconds -= remaining;
        }
      }

      @Override
      float remaining() {
        return Float.POSITIVE_INFINITY;
      }
    };
  };
}
