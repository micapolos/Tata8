package micapolos.tata8;

public final class Mouse {
  public final IntVector position = new IntVector();
  public final Button button = new Button();
  public boolean isOutside = false;

  Mouse() {}

  void update() {
    button.update();
  }
}
