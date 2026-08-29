package micapolos.tata8;

public abstract class Animation {
  public abstract void start();

  abstract float advance(float seconds);

  public final boolean update() {
    return advance(1/60f) > 0;
  }

  public static Animation instant(Action action) {
    return new Animation() {
      @Override
      public void start() {
        action.execute();
      }

      @Override
      float advance(float seconds) {
        return seconds;
      }
    };
  }

  public static Animation instant() {
    return instant(() -> {});
  }

  public static Animation continuous(Updater updater) {
    return new Animation() {
      @Override
      public void start() {

      }

      @Override
      float advance(float seconds) {
        updater.update(seconds);
        return 0;
      }
    };
  }

  public static Animation continuous() {
    return continuous(seconds -> {});
  }

  public static Animation step(float seconds, Action action) {
    return instant(action).then(pause(seconds));
  }

  public Animation stretch(float scale) {
    return new Animation() {
      @Override
      public void start() {
        Animation.this.start();
      }

      @Override
      float advance(float seconds) {
        return Animation.this.advance(seconds / scale);
      }
    };
  }

  public static Animation pause(float pauseSeconds) {
    return new Animation() {
      float remainingSeconds;

      @Override
      public void start() {
        remainingSeconds = pauseSeconds;
      }

      @Override
      float advance(float seconds) {
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

  public Animation startWhen(Event event) {
    return start(when(event, this));
  }

  public final Animation then(Animation secondAnimation) {
    return new Animation() {
      boolean isRunningFirst;

      @Override
      public void start() {
        Animation.this.start();
        isRunningFirst = true;
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
      public void start() {
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

  public final Animation repeat() {
    return new Animation() {
      @Override
      public void start() {
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

  public final Animation repeat(int times) {
    return new Animation() {
      int counter;

      @Override
      public void start() {
        Animation.this.start();
        counter = times;
      }

      @Override
      float advance(float seconds) {
        while (true) {
          float overflow = Animation.this.advance(seconds);
          if (overflow == 0) {
            return 0;
          } else {
            counter--;
            if (counter == 0) {
              return 0;
            } else {
              Animation.this.start();
              seconds = overflow;
            }
          }
        }
      };
    };
  }

  public static Animation random(Animation... animations) {
    return new Animation() {
      Animation current;

      @Override
      public void start() {
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

  public static When when(Event event, Animation animation) {
    return new When(event, animation);
  }

  public record When(Event event, Animation animation) {}

  public static Animation start(When... whenCases) {
    return new Animation() {
      Animation currentAnimation;

      @Override
      public void start() {}

      @Override
      float advance(float seconds) {
        for (When option : whenCases) {
          if (option.event.didHappen()) {
            currentAnimation = option.animation;
            currentAnimation.start();
            break;
          }
        }

        return currentAnimation == null
            ? 0
            : currentAnimation.advance(seconds);
      }
    };
  }

  public Animation stopWhen(Event event) {
    return new Animation() {
      boolean isRunning = false;

      @Override
      public void start() {
        Animation.this.start();
        isRunning = true;
      }

      @Override
      float advance(float seconds) {
        if (isRunning) {
          if (event.didHappen()) {
            isRunning = false;
            return 0;
          } else {
            return Animation.this.advance(seconds);
          }
        } else {
          return 0;
        }
      }
    };
  }

  public Animation runWhile(Condition condition) {
    return new Animation() {
      @Override
      public void start() {
        Animation.this.start();
      }

      @Override
      float advance(float seconds) {
        return condition.isHappening()
          ? Animation.this.advance(seconds)
          : 0;
      }
    };
  }

  public static Option during(Condition condition, Animation animation) {
    return new Option(condition, animation);
  }

  public record Option(Condition condition, Animation animation) {}

  public static Animation oneOf(Option... options) {
    return new Animation() {
      @Override
      public void start() {
        for (Option option : options) {
          option.animation.start();
        }
      }

      @Override
      float advance(float seconds) {
        for (Option option : options) {
          if (option.condition().isHappening()) {
            return option.animation.advance(seconds);
          }
        }
        return 0;
      }
    };
  }
}
