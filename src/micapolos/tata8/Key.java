package micapolos.tata8;

public final class Key {
  public boolean wasPressed;
  public boolean isPressed;

  public boolean isPressed() {
    return isPressed;
  }

  public boolean pressed() {
    return !wasPressed && isPressed;
  }

  public boolean released() {
    return wasPressed && !isPressed;
  }

  void press() {
    isPressed = true;
  }

  void release() {
    isPressed = false;
  }

  void update() {
    wasPressed = isPressed;
  }
}
