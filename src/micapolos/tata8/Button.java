package micapolos.tata8;

public final class Button {
  boolean wasPressed;
  boolean isPressed;
  boolean didPress;

  public boolean isPressed() {
    return isPressed;
  }

  public boolean didPress() {
    return didPress;
  }

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
