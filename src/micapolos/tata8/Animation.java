package micapolos.tata8;

import java.util.function.BooleanSupplier;

public abstract class Animation {
  abstract void start();

  abstract float advance(float seconds);

  public final boolean update() {
    return advance(1/60f) > 0;
  }

  public static Animation set(Sprite sprite, Image image) {
    return run(() -> sprite.image = image);
  }

  public static Animation elastic(Position src, Position dst) {
    return run(() -> src.setElastic(dst));
  }

  public static Animation play(Channel channel, Note note) {
    return run(() -> channel.play(note));
  }

  public Animation startWhen(BooleanSupplier booleanSupplier) {
    return new Animation() {
      boolean didStart;

      @Override
      void start() {

      }

      @Override
      float advance(float seconds) {
        if (booleanSupplier.getAsBoolean()) {
          didStart = true;
          Animation.this.start();
        }
        return didStart ? Animation.this.advance(seconds) : 0;
      }
    };
  }

  public Animation startWhenPressed(Key key) {
    return startWhen(key::didPress);
  }

  public static Animation run(Runnable runnable) {
    return new Animation() {
      @Override
      void start() {
        runnable.run();
      }

      @Override
      float advance(float seconds) {
        return seconds;
      }
    };
  }

  public static Animation begin() {
    return new Animation() {
      @Override
      void start() {

      }

      @Override
      float advance(float seconds) {
        return seconds;
      }
    };
  }

  public static Animation stop() {
    return new Animation() {
      @Override
      void start() {

      }

      @Override
      float advance(float seconds) {
        return 0;
      }
    };
  }

  public static Animation pause(Duration duration) {
    return new Animation() {
      float remainingSeconds;

      @Override
      public void start() {
        remainingSeconds = duration.seconds;
      }

      @Override
      public float advance(float seconds) {
        float diff = remainingSeconds - seconds;
        if (diff >= 0) {
          remainingSeconds = diff;
          return 0;
        } else {
          remainingSeconds = 0;
          return -diff;
        }
      }
    };
  }

  public final Animation then(Animation secondAnimation) {
    return new Animation() {
      boolean isRunningFirst;

      @Override
      void start() {
        isRunningFirst = true;
        Animation.this.start();
      }

      @Override
      float advance(float seconds) {
        if (isRunningFirst) {
          float overflow = Animation.this.advance(seconds);
          if (overflow == 0) {
            return 0;
          } else {
            isRunningFirst = false;
            secondAnimation.start();
            return secondAnimation.advance(seconds);
          }
        } else {
          return secondAnimation.advance(seconds);
        }
      }
    };
  }

  public static Animation sequence(Animation... animations) {
    return new Animation() {
      int index;

      @Override
      void start() {
        index = 0;
        Animation animation = current();
        if (animation != null) {
          animation.start();
        }
      }

      @Override
      float advance(float seconds) {
        Animation animation = current();
        while (true) {
          if (animation == null) {
            return seconds;
          } else {
            seconds = animation.advance(seconds);
            if (seconds == 0) {
              return 0;
            } else {
              index++;
              animation = current();
              if (animation != null) {
                animation.start();
              }
            }
          }
        }
      }

      Animation current() {
        return index < animations.length ? animations[index] : null;
      }
    };
  }

  public final Animation repeat(int times) {
    Animation animation = begin();
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
      float advance(float seconds) {
        while (true) {
          seconds = Animation.this.advance(seconds);
          if (seconds == 0) {
            return 0;
          } else {
            Animation.this.start();
          }
        }
      }
    };
  }

  public static Animation random(Animation... animations) {
    return new Animation() {
      Animation current;

      @Override
      void start() {
        if (animations.length == 0) {
          current = null;
        } else {
          current = animations[Random.until(animations.length)];
          current.start();
        }
      }

      @Override
      float advance(float seconds) {
        return current == null
            ? seconds
            : current.advance(seconds);
      }
    };
  }
}
