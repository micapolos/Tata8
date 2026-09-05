package micapolos.sad;

import micapolos.tata8.Game;

import static micapolos.Leo.*;

public abstract class Boolean extends Component {
  abstract boolean get();

  public static final Boolean reset = new Boolean() {
    boolean value;

    @Override
    boolean get() {
      return value;
    }

    @Override
    void update() {
      value = Game.keys.reset.pressed();
    }

    @Override
    String toLeo() {
      return leo("reset");
    }
  };

  public Component ifTrue(Component component) {
    return new Component() {
      @Override
      void update() {
        if (Boolean.this.get()) {
          component.update();
        }
      }

      @Override
      void compileDeps(Compiler compiler) {
        compiler.compile(Boolean.this);
        compiler.compile(component);
      }

      @Override
      String toLeo() {
        return leo("select", leo("if true", this), leo("then", component));
      }
    };
  }

  public Component whileTrue(Component component) {
    Component root = component.compile();

    return new Component() {
      @Override
      void init() {
        root.init();
      }

      @Override
      void update() {
        root.update();
      }

      @Override
      float step(float seconds) {
        return Boolean.this.get() ? component.step(seconds) : seconds;
      }

      @Override
      void compileDeps(Compiler compiler) {
        compiler.compile(Boolean.this);
      }

      @Override
      String toLeo() {
        return leo("select", leo("while true", this), leo("do", component));
      }
    };
  }
}
