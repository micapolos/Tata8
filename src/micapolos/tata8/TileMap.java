package micapolos.tata8;

public final class TileMap {
  final Cell[][] cells;
  public final FinalSize size;
  public final FinalSize tileSize;
  public final FloatVector camera = new FloatVector();

  TileMap(FinalSize size, FinalSize tileSize, Cell[][] cells) {
    this.size = size;
    this.tileSize = tileSize;
    this.cells = cells;
  }

  public Cell cell(int x, int y) {
    return Arrays.get(cells, x, y);
  }

  static TileMap create(int width, int height, int tileWidth, int tileHeight) {
    Cell[][] cells = new Cell[width][height];
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        cells[x][y] = new Cell();
      }
    }
    return new TileMap(
        new FinalSize(width, height),
        new FinalSize(tileWidth, tileHeight),
        cells);
  }

  void drawOn(Canvas canvas) {
    int startRow = (int) Math.floor(camera.y / tileSize.height);
    int startY = (int) Math.round(startRow * tileSize.height - camera.y);
    int startColumn = (int) Math.floor(camera.x / tileSize.width);
    int startX = (int) Math.round(startColumn * tileSize.width - camera.x);
    int y = startY;
    int row = startRow;
    while (y < Game.HEIGHT) {
      int x = startX;
      int column = startColumn;
      while (x < Game.WIDTH) {
        Cell cell = cell(column, row);
        if (cell != null) {
          Tile tile = cell.tile;
          if (tile != null) {
            Image image = tile.image;
            if (image != null) {
              canvas.draw(image, x, y, cell.flip.x, cell.flip.y);
            }
          }
        }
        x += tileSize.width;
        column++;
      }
      y += tileSize.height;
      row++;
    }
  }

  @Override
  public String toString() {
    return String.format("tileMap(%s), tile(%s))", size, tileSize);
  }
}
