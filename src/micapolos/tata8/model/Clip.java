package micapolos.tata8.model;

import micapolos.tata8.Random;

import java.util.function.IntFunction;

// TODO: Consider renaming to Span
public abstract class Clip {
  /**
   * Starts the clip.
   */
  abstract void start();

  /**
   * Performs the next step of the clip.
   *
   * @param seconds the number of seconds to step.
   * @return zero if there are more steps, or the number of seconds after the last step ended.
   */
  abstract float step(float seconds);

  public static Clip clip(Action start, Stepper stepper) {
    return new Clip() {
      @Override
      void start() {
        start.execute();
      }

      @Override
      float step(float seconds) {
        return stepper.step(seconds);
      }
    };
  }

  @Deprecated(forRemoval = true)
  public final void startInternal() {
    start();
  }

  @Deprecated(forRemoval = true)
  public final void advanceInternal(float seconds) {
    step(seconds);
  }

  public static Clip instant(Action action) {
    return new Clip() {
      @Override
      void start() {
        action.execute();
      }

      @Override
      float step(float seconds) {
        return seconds;
      }
    };
  }

  public static Clip instant(Action... actions) {
    return instant(Action.sequence(actions));
  }

  public static Clip instant() {
    return instant(Action.noAction);
  }

  public static Clip animated(Stepper stepper) {
    return new Clip() {
      @Override
      void start() {

      }

      @Override
      float step(float seconds) {
        return stepper.step(seconds);
      }
    };
  }

  public static final Clip EMPTY = animated(Stepper.EMPTY);

  public static Clip frame(Action action) {
    return frame(1, action);
  }

  public static Clip frame(float seconds, Action action) {
    return instant(action).then(pause(seconds));
  }

  public final Clip stretch(float scale) {
    return new Clip() {
      @Override
      void start() {
        Clip.this.start();
      }

      @Override
      float step(float seconds) {
        return Clip.this.step(seconds / scale);
      }
    };
  }

  public final Clip delay(float seconds) {
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
      float step(float seconds) {
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

  public final Clip then(Clip secondClip) {
    return new Clip() {
      boolean isRunningFirst;

      @Override
      void start() {
        Clip.this.start();
        isRunningFirst = true;
      }

      @Override
      float step(float seconds) {
        if (isRunningFirst) {
          float overflow = Clip.this.step(seconds);
          if (overflow == 0) {
            return 0;
          } else {
            isRunningFirst = false;
            secondClip.start();
            return secondClip.step(seconds);
          }
        } else {
          return secondClip.step(seconds);
        }
      }
    };
  }

  public static Clip sequence(int count, IntFunction<Clip> clipFunction) {
    Clip[] clips = new Clip[count];
    for (int i = 0; i < count; i++) {
      clips[i] = clipFunction.apply(i);
    }
    return sequence(clips);
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
      float step(float seconds) {
        Clip clip = current();
        while (true) {
          if (clip == null) {
            return seconds;
          } else {
            seconds = clip.step(seconds);
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

  public static Clip parallel(int count, IntFunction<Clip> clipFunction) {
    Clip[] clips = new Clip[count];
    for (int i = 0; i < count; i++) {
      clips[i] = clipFunction.apply(i);
    }
    return parallel(clips);
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
      float step(float seconds) {
        float overflow = Float.POSITIVE_INFINITY;
        for (Clip clip : clips) {
          overflow = java.lang.Math.min(overflow, clip.step(seconds));
        }
        return overflow;
      }
    };
  }

  public static Clip select(ConditionOption... conditionOptions) {
    return new Clip() {
      @Override
      void start() {
        for (ConditionOption conditionOption : conditionOptions) {
          conditionOption.clip.start();
        }
      }

      @Override
      float step(float seconds) {
        for (ConditionOption conditionOption : conditionOptions) {
          if (conditionOption.condition().get()) {
            return conditionOption.clip.step(seconds);
          }
        }
        return 0;
      }
    };
  }

  public static Clip repeat(int times, Clip clip) {
    return clip.repeat(times);
  }

  public final Clip repeat() {
    return new Clip() {
      @Override
      void start() {
        Clip.this.start();
      }

      @Override
      float step(float seconds) {
        while (true) {
          seconds = Clip.this.step(seconds);
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
      float step(float seconds) {
        while (true) {
          if (counter == 0) {
            return 0;
          }
          float overflow = Clip.this.step(seconds);
          if (overflow == 0) {
            return 0;
          } else {
            counter--;
          }
        }
      }
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
      float step(float seconds) {
        return current == null
          ? seconds
          : current.step(seconds);
      }
    };
  }

  public static EventOption on(Event event, Action action) {
    return on(event, instant(action));
  }

  public static EventOption on(Event event, Clip clip) {
    return when(event, clip);
  }

  @Deprecated
  public static EventOption when(Event event, Clip clip) {
    return new EventOption(event, clip);
  }

  public static final class EventOption {
    final Event event;
    final Clip clip;

    EventOption(Event event, Clip clip) {
      this.event = event;
      this.clip = clip;
    }
  }

  public static Clip select(EventOption... options) {
    return instant().thenSelect(options);
  }

  public final Clip thenSelect(EventOption... options) {
    return new Clip() {
      EventOption selectedOption;

      @Override
      void start() {
        Clip.this.start();
      }

      @Override
      float step(float seconds) {
        for (EventOption option : options) {
          if (option.event.occurs()) {
            option.clip.start();
            selectedOption = option;
          }
        }

        Clip clip = selectedOption != null ? selectedOption.clip : Clip.this;
        return clip.step(seconds);
      }
    };
  }

  public final Clip stop(Event event) {
    return new Clip() {
      boolean isRunning = false;

      @Override
      void start() {
        Clip.this.start();
        isRunning = true;
      }

      @Override
      float step(float seconds) {
        if (isRunning) {
          if (event.occurs()) {
            isRunning = false;
            return 0;
          } else {
            return Clip.this.step(seconds);
          }
        } else {
          return 0;
        }
      }
    };
  }

  public final Clip runWhile(Boolean condition) {
    return new Clip() {
      @Override
      void start() {
        Clip.this.start();
      }

      @Override
      float step(float seconds) {
        return condition.get()
          ? Clip.this.step(seconds)
          : 0;
      }
    };
  }

  public static ConditionOption when(Boolean condition, Clip clip) {
    return new ConditionOption(condition, clip);
  }

  public record ConditionOption(Boolean condition, Clip clip) {
  }

  public final void show() {
    Game.add(this);
    Game.show();
  }

  public final void showWith(Showable... showables) {
    Game.add(seconds -> {
      for (Showable showable : showables) {
        micapolos.tata8.Game.log(showable);
      }
      return seconds;
    });
    Game.add(this);
    Game.show();
  }

  static void main() {
    Clip.EMPTY.show();
  }
}
