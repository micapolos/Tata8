package micapolos.sad;

import java.util.ArrayList;
import java.util.List;

import static micapolos.Leo.*;

public abstract class Component {
  static class Compiler {
    List<Component> components = new ArrayList<>();

    void compile(Component component) {
      if (component.id == -1) {
        throw new IllegalArgumentException("recursion");
      } else if (component.id == 0) {
        component.id = -1;
        component.compileDeps(this);
        components.add(component);
        component.id = components.size();
      }
    }
  }

  int id = 0;

  void init() {
  }

  float step(float seconds) {
    return seconds;
  }

  void update() {
  }

  abstract String toLeo();

  void compileDeps(Compiler compiler) {
  }

  public static Component pause(double seconds) {
    return new Component() {
      float remaining = (float) seconds;

      @Override
      void init() {
        remaining = (float) seconds;
      }

      @Override
      float step(float seconds) {
        remaining -= seconds;
        return remaining >= 0 ? 0 : -remaining;
      }

      @Override
      String toLeo() {
        return leo("pause", seconds);
      }

      @Override
      public String toString() {
        return toLeo();
      }
    };
  }

  public static Component parallel(Component... components) {
    return new Component() {
      @Override
      void init() {
        for (Component component : components) {
          component.init();
        }
      }

      @Override
      float step(float seconds) {
        float remaining = Float.POSITIVE_INFINITY;
        for (Component component : components) {
          remaining = Math.min(remaining, component.step(seconds));
        }
        return remaining;
      }

      @Override
      void update() {
        for (Component component : components) {
          component.update();
        }
      }

      @Override
      void compileDeps(Compiler compiler) {
        for (Component component : components) {
          compiler.compile(component);
        }
      }

      @Override
      String toLeo() {
        return leo("parallel", components);
      }
    };
  }

  public static Component sequence(Component... components) {
    return new Component() {
      int currentIndex;
      boolean needsInit;

      @Override
      void init() {
        currentIndex = 0;
        needsInit = true;
      }

      @Override
      float step(float seconds) {
        while (true) {
          if (currentIndex == components.length) {
            return seconds;
          } else {
            Component component = components[currentIndex];
            if (needsInit) {
              component.init();
              needsInit = false;
            }
            seconds = component.step(seconds);
            if (seconds == 0) {
              return 0;
            } else {
              currentIndex++;
              needsInit = true;
            }
          }
        }
      }

      @Override
      String toLeo() {
        return leo("sequence", components);
      }
    };
  }

  final Component compile() {
    Compiler compiler = new Compiler();
    compiler.compile(this);
    return parallel(compiler.components.toArray(new Component[0]));
  }
}
