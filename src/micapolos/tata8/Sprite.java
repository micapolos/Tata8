package micapolos.tata8;

public final class Sprite implements Comparable<Sprite> {
  public Image image;
  public boolean isHidden;
  public final FloatVector position = new FloatVector();
  public final FloatVector anchor = new FloatVector();
  public final FloatVector scale = new FloatVector(1f, 1f);
  public final BoolVector flip = new BoolVector();
  public float angle;
  public int zIndex;

  Sprite() {}

  @Override
  public int compareTo(Sprite o) {
    return Integer.compare(zIndex, o.zIndex);
  }
}
