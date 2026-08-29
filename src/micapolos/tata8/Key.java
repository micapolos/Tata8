package micapolos.tata8;

public final class Key {
  boolean wasPressed;
  boolean isPressed;
  boolean didPress;

  public boolean isPressed() {
    return isPressed;
  }

  public boolean didPress() {
    return didPress;
  }

  public Event didPressEvent() {
    return () -> didPress;
  }

  public Condition isPressedCondition() {
    return () -> isPressed;
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
