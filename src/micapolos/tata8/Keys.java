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

  final Key[] array = new Key[] { z, x, left, right, up, down, reset };

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
      case KeyEvent.VK_R -> (event.getModifiersEx() & KeyEvent.META_DOWN_MASK) != 0 ? reset : null;
      default -> null;
    };
  }

  @Override
  public String toString() {
    return String.format(
        "keys(left: %s, right: %s, up: %s, down: %s, Z: %s, X: %s, RESET: %s)",
        left.isPressed,
        right.isPressed,
        up.isPressed,
        down.isPressed,
        z.isPressed,
        x.isPressed,
        reset.isPressed);
  }
}
