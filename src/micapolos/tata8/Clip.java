package micapolos.tata8;

import java.util.function.IntToDoubleFunction;

public abstract class Clip {
  /**
   * Starts the clip.
   */
  abstract void start();

  /**
   * Advances the clip.
   *
   * @param seconds the number of seconds to advance the clip.
   * @return the number of remaining seconds to advance, if the clip finished in this step.
   */
  abstract float advance(float seconds);

  public static Clip instant(Action action) {
    return new Clip() {
      @Override
      void start() {
        action.execute();
      }

      @Override
      float advance(float seconds) {
        return seconds;
      }
    };
  }

  public static Clip instant() {
    return instant(() -> {
    });
  }

  public static Clip continuous(Updater updater) {
    return new Clip() {
      @Override
      void start() {

      }

      @Override
      float advance(float seconds) {
        updater.update(seconds);
        return 0;
      }
    };
  }

  public static Clip continuous() {
    return continuous(seconds -> {
    });
  }

  public static Clip frame(float seconds, Action action) {
    return instant(action).then(pause(seconds));
  }

  public Clip stretch(float scale) {
    return new Clip() {
      @Override
      void start() {
        Clip.this.start();
      }

      @Override
      float advance(float seconds) {
        return Clip.this.advance(seconds / scale);
      }
    };
  }

  public Clip delay(float seconds) {
    return pause(seconds).then(this);
  }

  public static Clip pause(float pauseSeconds) {
    return new Clip() {
      float remainingSeconds;

      @Override
      void start() {
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

  public Clip startWhen(Event event) {
    return start(when(event, this));
  }

  public final Clip then(Clip secondClip) {
    return new Clip() {
      boolean isRunningFirst;

      @Override
      void start() {
        Clip.this.start();
        isRunningFirst = true;
      }

      @Override
      float advance(float seconds) {
        if (isRunningFirst) {
          float overflow = Clip.this.advance(seconds);
          if (overflow == 0) {
            return 0;
          } else {
            isRunningFirst = false;
            secondClip.start();
            return secondClip.advance(seconds);
          }
        } else {
          return secondClip.advance(seconds);
        }
      }
    };
  }

  public static Clip sequence(Clip... clips) {
    return new Clip() {
      int index;

      @Override
      void start() {
        index = 0;
        Clip clip = current();
        if (clip != null) {
          clip.start();
        }
      }

      @Override
      float advance(float seconds) {
        Clip clip = current();
        while (true) {
          if (clip == null) {
            return seconds;
          } else {
            seconds = clip.advance(seconds);
            if (seconds == 0) {
              return 0;
            } else {
              index++;
              clip = current();
              if (clip != null) {
                clip.start();
              }
            }
          }
        }
      }

      Clip current() {
        return index < clips.length ? clips[index] : null;
      }
    };
  }

  public static Clip parallel(Clip... clips) {
    return new Clip() {
      @Override
      void start() {
        for (Clip clip : clips) {
          clip.start();
        }
      }

      @Override
      float advance(float seconds) {
        float overflow = Float.POSITIVE_INFINITY;
        for (Clip clip : clips) {
          overflow = java.lang.Math.min(overflow, clip.advance(seconds));
        }
        return overflow;
      }
    };
  }

  public final Clip repeat() {
    return new Clip() {
      @Override
      void start() {
        Clip.this.start();
      }

      @Override
      float advance(float seconds) {
        while (true) {
          seconds = Clip.this.advance(seconds);
          if (seconds == 0) {
            return 0;
          } else {
            Clip.this.start();
          }
        }
      }
    };
  }

  public final Clip repeat(int times) {
    return new Clip() {
      int counter;

      @Override
      void start() {
        Clip.this.start();
        counter = times;
      }

      @Override
      float advance(float seconds) {
        while (true) {
          float overflow = Clip.this.advance(seconds);
          if (overflow == 0) {
            return 0;
          } else {
            counter--;
            if (counter == 0) {
              return 0;
            } else {
              Clip.this.start();
              seconds = overflow;
            }
          }
        }
      }

      ;
    };
  }

  public static Clip random(Clip... clips) {
    return new Clip() {
      Clip current;

      @Override
      void start() {
        if (clips.length == 0) {
          current = null;
        } else {
          current = clips[Random.until(clips.length)];
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

  public static When when(Event event, Clip clip) {
    return new When(event, clip);
  }

  public record When(Event event, Clip clip) {
  }

  public static Clip start(When... whenCases) {
    return new Clip() {
      Clip currentClip;

      @Override
      void start() {
      }

      @Override
      float advance(float seconds) {
        for (When option : whenCases) {
          if (option.event.didHappen()) {
            currentClip = option.clip;
            currentClip.start();
            break;
          }
        }

        return currentClip == null
          ? 0
          : currentClip.advance(seconds);
      }
    };
  }

  public Clip stopWhen(Event event) {
    return new Clip() {
      boolean isRunning = false;

      @Override
      void start() {
        Clip.this.start();
        isRunning = true;
      }

      @Override
      float advance(float seconds) {
        if (isRunning) {
          if (event.didHappen()) {
            isRunning = false;
            return 0;
          } else {
            return Clip.this.advance(seconds);
          }
        } else {
          return 0;
        }
      }
    };
  }

  public Clip runWhile(Condition condition) {
    return new Clip() {
      @Override
      void start() {
        Clip.this.start();
      }

      @Override
      float advance(float seconds) {
        return condition.isHappening()
          ? Clip.this.advance(seconds)
          : 0;
      }
    };
  }

  public static Option during(Condition condition, Clip clip) {
    return new Option(condition, clip);
  }

  public record Option(Condition condition, Clip clip) {
  }

  public static Clip oneOf(Option... options) {
    return new Clip() {
      @Override
      void start() {
        for (Option option : options) {
          option.clip.start();
        }
      }

      @Override
      float advance(float seconds) {
        for (Option option : options) {
          if (option.condition().isHappening()) {
            return option.clip.advance(seconds);
          }
        }
        return 0;
      }
    };
  }
}
