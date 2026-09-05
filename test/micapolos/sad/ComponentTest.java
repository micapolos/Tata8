package micapolos.sad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static micapolos.Leo.leo;
import static org.junit.jupiter.api.Assertions.*;

class ComponentTest {
  List<String> captured;

  @BeforeEach
  void before() {
    captured = new ArrayList<>();
  }

  @Test
  void init() {
  }

  @Test
  void step() {
  }

  @Test
  void update() {
  }

  @Test
  void acceptDependencies() {
  }

  @Test
  void acceptDependenciesOnce() {
  }

  @Test
  void pause() {
    var root = Component.sequence(capture("c1"), Component.pause(10), capture("c1")).compile();
    root.init();
    assertEquals(List.of("init: c1"), captured);
    captured.clear();

    root.update();
    assertEquals(List.of("init: c1"), captured);
    captured.clear();
  }

  @Test
  void parallel() {
    var c1 = capture("c1");
    var c2 = capture("c2", c1);
    var c3 = capture("c3", c1, c2);
    var root = c3.compile();

    root.init();
    assertEquals(List.of("init: c1", "init: c2", "init: c3"), captured);
    captured.clear();

    root.update();
    assertEquals(List.of("update: c1", "update: c2", "update: c3"), captured);
    captured.clear();

    root.step(10);
    assertEquals(List.of("step: c1, 10.0", "step: c2, 10.0", "step: c3, 10.0"), captured);
  }

  @Test
  void sequence() {
    var c1 = capture("c1");
    var c2 = capture("c2");
    var c3 = capture("c3");
  }

  @Test
  void toRootComponent() {
  }

  public Component capture(String name, Component... deps) {
    return new Component() {
      @Override
      void init() {
        captured.add("init: " + name);
      }

      @Override
      float step(float seconds) {
        captured.add("step: " + name + ", " + seconds);
        return seconds;
      }

      void update() {
        captured.add("update: " + name);
      }

      @Override
      void compileDeps(Compiler compiler) {
        for (Component dep : deps) {
          compiler.compile(dep);
        }
      }

      @Override
      String toLeo() {
        return leo(name, deps);
      }
    };
  }
}