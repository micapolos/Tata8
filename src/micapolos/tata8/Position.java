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

  public void add(Speed speed) {
    set(x + speed.x, y + speed.y);
  }

  public void setElastic(Position position) {
    set(Math.elastic(x, position.x), Math.elastic(y, position.y));
  }

  @Override
  public String toString() {
    return "vector(" + x + ", " + y + ")";
  }
}
