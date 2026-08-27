package micapolos.tata8;

public final class Camera {
  public final Position position = new Position();

  Camera() {}

  @Override
  public String toString() {
    return String.format("camera(%s)", position);
  }
}
