package micapolos.zexy;

import micapolos.tata8.Random;

import java.util.function.IntFunction;

import static micapolos.zexy.Action.*;
import static micapolos.Leo.*;

// TODO: Consider renaming to Span
public abstract class Animation extends Component {
  /**
   * Starts this animation.
   */
  abstract void start();

  /**
   * Performs the next step of this animation.
   *
   * @param seconds the number of seconds to step.
   * @return zero if there are more steps, or the number of seconds after the last step ended.
   */
  abstract float step(float seconds);

  @Override
  void addRunners() {
    Game.add(new Runner() {
      @Override
      public void init() {
        start();
      }

      @Override
      public void update(float seconds) {
        step(seconds);
      }
    });
  }

  public static Animation animation(Activity activity) {
    return animation(noAction, activity);
  }

  public static Animation animation(Action start, Activity activity) {
    return new Animation() {
      @Override
      void start() {
        start.execute();
      }

      @Override
      float step(float seconds) {
        activity.advance(seconds);
        return seconds;
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

  public static Animation instant(Action action) {
    return new Animation() {
      @Override
      void start() {
        action.execute();
      }

      @Override
      float step(float seconds) {
        return seconds;
      }

      @Override
      void addRunners() {
        action.addRunnersOnce();
      }

      @Override
      public String toString() {
        return leo("instant", action);
      }
    };
  }

  public static Animation instant(Action... actions) {
    return instant(Action.sequence(actions));
  }

  public static Animation instant() {
    return instant(noAction);
  }

  public static Animation with(Activity activity) {
    return new Animation() {
      @Override
      void start() {

      }

      @Override
      float step(float seconds) {
        activity.advance(seconds);
        return 0;
      }
    };
  }

  public static final Animation EMPTY_ANIMATION = with(Activity.noActivity);

  public static Animation frame(Action action) {
    return frame(1, action);
  }

  public static Animation frame(float seconds, Action action) {
    return sequence(instant(action), pause(seconds));
  }

  public final Animation stretch(float scale) {
    return new Animation() {
      @Override
      void start() {
        Animation.this.start();
      }

      @Override
      float step(float seconds) {
        return Animation.this.step(seconds / scale);
      }
    };
  }

  public final Animation delay(float seconds) {
    return pause(seconds).then(this);
  }

  public static Animation pause(float pauseSeconds) {
    return new Animation() {
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

      @Override
      public String toString() {
        return leo("pause", pauseSeconds);
      }
    };
  }

  public final Animation then(Animation secondAnimation) {
    return new Animation() {
      boolean isRunningFirst;

      @Override
      void start() {
        Animation.this.start();
        isRunningFirst = true;
      }

      @Override
      float step(float seconds) {
        if (isRunningFirst) {
          float overflow = Animation.this.step(seconds);
          if (overflow == 0) {
            return 0;
          } else {
            isRunningFirst = false;
            secondAnimation.start();
            return secondAnimation.step(seconds);
          }
        } else {
          return secondAnimation.step(seconds);
        }
      }
    };
  }

  public static Animation sequence(int count, IntFunction<Animation> animationFunction) {
    Animation[] animations = new Animation[count];
    for (int i = 0; i < count; i++) {
      animations[i] = animationFunction.apply(i);
    }
    return sequence(animations);
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
      float step(float seconds) {
        Animation animation = current();
        while (true) {
          if (animation == null) {
            return seconds;
          } else {
            seconds = animation.step(seconds);
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

      @Override
      public String toString() {
        return leo("sequence", animations);
      }
    };
  }

  public static Animation parallel(int count, IntFunction<Animation> animationFunction) {
    Animation[] animations = new Animation[count];
    for (int i = 0; i < count; i++) {
      animations[i] = animationFunction.apply(i);
    }
    return parallel(animations);
  }

  public static Animation parallel(Animation... animations) {
    return new Animation() {
      @Override
      void start() {
        for (Animation animation : animations) {
          animation.start();
        }
      }

      @Override
      float step(float seconds) {
        float overflow = Float.POSITIVE_INFINITY;
        for (Animation animation : animations) {
          overflow = java.lang.Math.min(overflow, animation.step(seconds));
        }
        return overflow;
      }

      @Override
      public String toString() {
        return leo("parallel", animations);
      }
    };
  }

  public static Animation select(ConditionOption... conditionOptions) {
    return new Animation() {
      @Override
      void start() {
        for (ConditionOption conditionOption : conditionOptions) {
          conditionOption.animation.start();
        }
      }

      @Override
      float step(float seconds) {
        for (ConditionOption conditionOption : conditionOptions) {
          if (conditionOption.condition().get()) {
            return conditionOption.animation.step(seconds);
          }
        }
        return 0;
      }

      @Override
      public String toString() {
        return leo("select", conditionOptions);
      }
    };
  }

  public static Animation repeat(int times, Animation animation) {
    return animation.repeat(times);
  }

  public final Animation repeat() {
    return new Animation() {
      @Override
      void start() {
        Animation.this.start();
      }

      @Override
      float step(float seconds) {
        while (true) {
          seconds = Animation.this.step(seconds);
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
      void start() {
        Animation.this.start();
        counter = times;
      }

      @Override
      float step(float seconds) {
        while (true) {
          if (counter == 0) {
            return 0;
          }
          float overflow = Animation.this.step(seconds);
          if (overflow == 0) {
            return 0;
          } else {
            counter--;
          }
        }
      }

      @Override
      public String toString() {
        return leo("repeat", this, leo("times", times));
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
      float step(float seconds) {
        return current == null
          ? seconds
          : current.step(seconds);
      }

      @Override
      public String toString() {
        return leo("random", animations);
      }
    };
  }

  public static EventOption on(Event event, Action action) {
    return on(event, instant(action));
  }

  public static EventOption on(Event event, Animation animation) {
    return when(event, animation);
  }

  @Deprecated
  public static EventOption when(Event event, Animation animation) {
    return new EventOption(event, animation);
  }

  public static final class EventOption {
    final Event event;
    final Animation animation;

    EventOption(Event event, Animation animation) {
      this.event = event;
      this.animation = animation;
    }

    @Override
    public String toString() {
      return leo("on", event, animation);
    }
  }

  public final Animation thenSelect(EventOption... options) {
    return new Animation() {
      EventOption selectedOption;

      @Override
      void start() {
        Animation.this.start();
      }

      @Override
      float step(float seconds) {
        for (EventOption option : options) {
          if (option.event.occurs()) {
            option.animation.start();
            selectedOption = option;
          }
        }

        Animation animation = selectedOption != null ? selectedOption.animation : Animation.this;
        return animation.step(seconds);
      }
    };
  }

  public static Animation select(EventOption... options) {
    return new Animation() {
      EventOption selectedOption;

      @Override
      void start() {

      }

      @Override
      float step(float seconds) {
        for (EventOption option : options) {
          if (option.event.occurs()) {
            option.animation.start();
            selectedOption = option;
          }
        }

        return selectedOption == null
            ? seconds
            : selectedOption.animation.step(seconds);
      }

      @Override
      public String toString() {
        return leo("select", options);
      }
    };
  }

  public final Animation stop(Event event) {
    return new Animation() {
      boolean isRunning = false;

      @Override
      void start() {
        Animation.this.start();
        isRunning = true;
      }

      @Override
      float step(float seconds) {
        if (isRunning) {
          if (event.occurs()) {
            isRunning = false;
            return 0;
          } else {
            return Animation.this.step(seconds);
          }
        } else {
          return 0;
        }
      }
    };
  }

  public final Animation runWhile(Boolean condition) {
    return new Animation() {
      @Override
      void start() {
        Animation.this.start();
      }

      @Override
      float step(float seconds) {
        return condition.get()
          ? Animation.this.step(seconds)
          : 0;
      }
    };
  }

  public static ConditionOption when(Boolean condition, Animation animation) {
    return new ConditionOption(condition, animation);
  }

  public record ConditionOption(Boolean condition, Animation animation) {
  }

  @Override
  public String toString() {
    return "an animation";
  }

  public final void show() {
    Game.add(this);
    super.show();
  }

  static void main() {
    select(on(Game.start, noAction)).show();
  }
}
