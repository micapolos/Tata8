package micapolos.tata8;

public interface Clip {
  /**
   * Starts the clip.
   */
  void start();

  /**
   * Advances the clip.
   *
   * @param seconds the number Aof seconds to advance the clip.
   * @return the number of remaining seconds if the clip finished while advancing.
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

  static Clip with(Animation animation) {
    return new Clip() {
      @Override
      public void start() {

      }

      @Override
      public float advance(float seconds) {
        animation.update(seconds);
        return 0;
      }
    };
  }

  Clip EMPTY = with(Animation.EMPTY);

  static Clip frame(Action action) {
    return frame(1, action);
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
    return start(option(event, this));
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

  static Clip parallel(ConditionOption... conditionOptions) {
    return new Clip() {
      @Override
      public void start() {
        for (ConditionOption conditionOption : conditionOptions) {
          conditionOption.clip.start();
        }
      }

      @Override
      public float advance(float seconds) {
        for (ConditionOption conditionOption : conditionOptions) {
          if (conditionOption.condition().isHappening()) {
            return conditionOption.clip.advance(seconds);
          }
        }
        return 0;
      }
    };
  }

  static Clip repeat(int times, Clip clip) {
    return clip.repeat(times);
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
          if (counter == 0) {
            return 0;
          }
          float overflow = Clip.this.advance(seconds);
          if (overflow == 0) {
            return 0;
          } else {
            counter--;
          }
        }
      }
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

  static EventOption option(Event event, Clip clip) {
    return new EventOption(event, clip);
  }

  final class EventOption {
    final Event event;
    final Clip clip;
    boolean selected;

    EventOption(Event event, Clip clip) {
      this.event = event;
      this.clip = clip;
    }
  }

  static Clip start(EventOption... options) {
    return new Clip() {
      @Override
      public void start() {
        for (EventOption option : options) {
          option.selected = false;
        }
      }

      @Override
      public float advance(float seconds) {
        for (EventOption option : options) {
          option.selected = option.event.didHappen();
          if (option.selected) {
            option.clip.start();
          }
        }

        Float remaining = Float.POSITIVE_INFINITY;
        for (EventOption option : options) {
          remaining = Math.min(remaining, option.clip.advance(seconds));
        }
        return remaining;
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

  static ConditionOption option(Condition condition, Clip clip) {
    return new ConditionOption(condition, clip);
  }

  record ConditionOption(Condition condition, Clip clip) {
  }

  static Clip first(ConditionOption... options) {
    return new Clip() {
      @Override
      public void start() {
        for (ConditionOption option : options) {
          option.clip.start();
        }
      }

      @Override
      public float advance(float seconds) {
        for (ConditionOption option : options) {
          if (option.condition().isHappening()) {
            return option.clip.advance(seconds);
          }
        }
        return 0;
      }
    };
  }
}
