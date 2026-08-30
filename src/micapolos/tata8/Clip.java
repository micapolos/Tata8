package micapolos.tata8;

public interface Clip {
  /**
   * Starts the clip.
   */
  void start();

  /**
   * Advances the clip.
   *
   * @param seconds the number of seconds to advance the clip.
   * @return the number of remaining seconds to advance, if the clip finished in this step.
   */
  float advance(float seconds);

  static Clip instant(Action action) {
    return new Clip() {
      @Override
      public void start() {
        action.execute();
      }

      @Override
      public float advance(float seconds) {
        return seconds;
      }
    };
  }

  static Clip instant() {
    return instant(() -> {
    });
  }

  static Clip continuous(Updater updater) {
    return new Clip() {
      @Override
      public void start() {

      }

      @Override
      public float advance(float seconds) {
        updater.update(seconds);
        return 0;
      }
    };
  }

  static Clip continuous() {
    return continuous(seconds -> {
    });
  }

  static Clip frame(float seconds, Action action) {
    return instant(action).then(pause(seconds));
  }

  default Clip stretch(float scale) {
    return new Clip() {
      @Override
      public void start() {
        Clip.this.start();
      }

      @Override
      public float advance(float seconds) {
        return Clip.this.advance(seconds / scale);
      }
    };
  }

  default Clip delay(float seconds) {
    return pause(seconds).then(this);
  }

  static Clip pause(float pauseSeconds) {
    return new Clip() {
      float remainingSeconds;

      @Override
      public void start() {
        remainingSeconds = pauseSeconds;
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

  default Clip startWhen(Event event) {
    return start(when(event, this));
  }

  default Clip then(Clip secondClip) {
    return new Clip() {
      boolean isRunningFirst;

      @Override
      public void start() {
        Clip.this.start();
        isRunningFirst = true;
      }

      @Override
      public float advance(float seconds) {
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

  static Clip sequence(Clip... clips) {
    return new Clip() {
      int index;

      @Override
      public void start() {
        index = 0;
        Clip clip = current();
        if (clip != null) {
          clip.start();
        }
      }

      @Override
      public float advance(float seconds) {
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

  static Clip parallel(Clip... clips) {
    return new Clip() {
      @Override
      public void start() {
        for (Clip clip : clips) {
          clip.start();
        }
      }

      @Override
      public float advance(float seconds) {
        float overflow = Float.POSITIVE_INFINITY;
        for (Clip clip : clips) {
          overflow = java.lang.Math.min(overflow, clip.advance(seconds));
        }
        return overflow;
      }
    };
  }

  default Clip repeat() {
    return new Clip() {
      @Override
      public void start() {
        Clip.this.start();
      }

      @Override
      public float advance(float seconds) {
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

  default Clip repeat(int times) {
    return new Clip() {
      int counter;

      @Override
      public void start() {
        Clip.this.start();
        counter = times;
      }

      @Override
      public float advance(float seconds) {
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

  static Clip random(Clip... clips) {
    return new Clip() {
      Clip current;

      @Override
      public void start() {
        if (clips.length == 0) {
          current = null;
        } else {
          current = clips[Random.until(clips.length)];
          current.start();
        }
      }

      @Override
      public float advance(float seconds) {
        return current == null
          ? seconds
          : current.advance(seconds);
      }
    };
  }

  static When when(Event event, Clip clip) {
    return new When(event, clip);
  }

  record When(Event event, Clip clip) {
  }

  static Clip start(When... whenCases) {
    return new Clip() {
      Clip currentClip;

      @Override
      public void start() {
      }

      @Override
      public float advance(float seconds) {
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

  default Clip stopWhen(Event event) {
    return new Clip() {
      boolean isRunning = false;

      @Override
      public void start() {
        Clip.this.start();
        isRunning = true;
      }

      @Override
      public float advance(float seconds) {
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

  default Clip runWhile(Condition condition) {
    return new Clip() {
      @Override
      public void start() {
        Clip.this.start();
      }

      @Override
      public float advance(float seconds) {
        return condition.isHappening()
          ? Clip.this.advance(seconds)
          : 0;
      }
    };
  }

  static Option during(Condition condition, Clip clip) {
    return new Option(condition, clip);
  }

  record Option(Condition condition, Clip clip) {
  }

  static Clip oneOf(Option... options) {
    return new Clip() {
      @Override
      public void start() {
        for (Option option : options) {
          option.clip.start();
        }
      }

      @Override
      public float advance(float seconds) {
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
