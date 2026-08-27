package micapolos.tata8;

public final class Background {
  public Color color = Color.TRANSPARENT;
  public final Canvas canvas;
  public final TileMap tileMap;

  Background(Canvas canvas, TileMap tileMap) {
    this.canvas = canvas;
    this.tileMap = tileMap;
  }

  @Override
  public String toString() {
    return String.format("background(%s, %s, %s)", color, canvas, tileMap);
  }
}
