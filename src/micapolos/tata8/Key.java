package micapolos.tata8;

public final class Key {
  boolean wasPressed;

  public boolean isPressed;
  public boolean didPress;

  void press() {
    isPressed = true;
  }

  void release() {
    isPressed = false;
  }

  void update() {
    didPress = !wasPressed && isPressed;
    wasPressed = isPressed;
  }
}
