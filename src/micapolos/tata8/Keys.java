package micapolos.tata8;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

public final class Keys {
  public final Key z = new Key();
  public final Key x = new Key();
  public final Key left = new Key();
  public final Key right = new Key();
  public final Key up = new Key();
  public final Key down = new Key();
  public final Key reset = new Key();
  public final Key slow = new Key();
  public final Key fast = new Key();
  public final Key shader = new Key();
  public final Key dusk = new Key();

  final Key[] array = new Key[]{z, x, left, right, up, down, reset, slow, fast, shader, dusk};

  final KeyListener listener = new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {
      handleEvent(e, Key::press);
    }

    @Override
    public void keyReleased(KeyEvent e) {
      handleEvent(e, Key::release);
    }

    private void handleEvent(KeyEvent event, Consumer<Key> keyConsumer) {
      Key key = keyForEvent(event);
      if (key != null) {
        keyConsumer.accept(key);
      }
    }
  };

  Key keyForEvent(KeyEvent event) {
    return switch (event.getKeyCode()) {
      case KeyEvent.VK_Z -> z;
      case KeyEvent.VK_X -> x;
      case KeyEvent.VK_LEFT -> left;
      case KeyEvent.VK_RIGHT -> right;
      case KeyEvent.VK_UP -> up;
      case KeyEvent.VK_DOWN -> down;
      case KeyEvent.VK_R -> reset;
      case KeyEvent.VK_S -> slow;
      case KeyEvent.VK_F -> fast;
      case KeyEvent.VK_D -> shader;
      case KeyEvent.VK_K -> dusk;
      default -> null;
    };
  }

  @Override
  public String toString() {
    return String.format(
      "keys(left: %s, right: %s, up: %s, down: %s, Z: %s, X: %s, RESET: %s, SLOW: %s, FAST: %s, SHADER: %s)",
      left.isPressed,
      right.isPressed,
      up.isPressed,
      down.isPressed,
      z.isPressed,
      x.isPressed,
      reset.isPressed,
      slow.isPressed,
      fast.isPressed,
      shader.isPressed
    );
  }
}
