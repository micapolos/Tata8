package micapolos.tata8;

public final class Position {
  public float x;
  public float y;

  public Position() {}

  public Position(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public void setZero() {
    set(0, 0);
  }

  public void set(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public void set(Position vector) {
    set(vector.x, vector.y);
  }

  public void add(float x, float y) {
    set(this.x + x, this.y + y);
  }

  public void add(Position vector) {
    add(vector.x, vector.y);
  }

  public void scale(float x, float y) {
    set(this.x * x, this.y * y);
  }

  public void scale(float t) {
    scale(t, t);
  }

  @Override
  public String toString() {
    return "vector(" + x + ", " + y + ")";
  }
}
