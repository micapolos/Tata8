package micapolos.tata8;

public final class Foreground {
  public final Canvas canvas;
  public final TileMap tileMap;

  Foreground(Canvas canvas, TileMap tileMap) {
    this.canvas = canvas;
    this.tileMap = tileMap;
  }

  @Override
  public String toString() {
    return String.format("foreground(%s, %s)", canvas, tileMap);
  }
}
