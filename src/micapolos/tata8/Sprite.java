package micapolos.tata8;

public final class Sprite implements Comparable<Sprite> {
  public Image image;
  public boolean isHidden;
  public final Position position = new Position();
  public final Anchor anchor = new Anchor();
  public final Scale scale = new Scale();
  public final Flip flip = new Flip();
  public float angle;
  public int zIndex;

  Sprite() {}

  @Override
  public int compareTo(Sprite o) {
    return Integer.compare(zIndex, o.zIndex);
  }

  @Override
  public String toString() {
    return isHidden
        ? "sprite(hidden)"
        : String.format("sprite(%s,%s,...)", position, flip);
  }
}
