package micapolos.tata8;

public final class Sprite {
  public Image image;
  public boolean isEnabled;
  public final IntVector position = new IntVector();
  public final IntVector anchor = new IntVector();
  public final IntVector zoom = new IntVector();
  public final BoolVector flip = new BoolVector();
  public int rotation;
}
