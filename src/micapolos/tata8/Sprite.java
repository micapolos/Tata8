package micapolos.tata8;

public final class Sprite {
  public Image image;
  public boolean isHidden;
  public final FloatVector position = new FloatVector();
  public final FloatVector anchor = new FloatVector();
  public final FloatVector scale = new FloatVector(1f, 1f);
  public final BoolVector flip = new BoolVector();
  public float angle;

  Sprite() {}
}
