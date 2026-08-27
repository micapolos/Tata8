package micapolos.tata8;

public final class TileMap {
  final Cell[][] cells;
  public final FinalSize size;
  public final FinalSize tileSize;

  TileMap(FinalSize size, FinalSize tileSize, Cell[][] cells) {
    this.size = size;
    this.tileSize = tileSize;
    this.cells = cells;
  }

  public Cell cell(int x, int y) {
    return Arrays.get(cells, x, y);
  }

  public void draw9Patch(int x1, int y1, int x2, int y2, TileSet tileSet, int tx, int ty) {
    for (int x = x1; x <= x2; x++) {
      for (int y = y1; y <= y2; y++) {
        cell(x, y).tile = tileSet.tile(
            tx + (x == x1 ? 0 : x == x2 ? 2 : 1),
            ty + (y == y1 ? 0 : y == y2 ? 2 : 1));
      }
    }
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

  void drawOn(Canvas canvas, float x, float y) {
    int startRow = (int) Math.floor(y / tileSize.height);
    int startY = (int) Math.round(startRow * tileSize.height - y);
    int startColumn = (int) Math.floor(x / tileSize.width);
    int startX = (int) Math.round(startColumn * tileSize.width - x);
    int tileY = startY;
    int row = startRow;
    while (tileY < Game.HEIGHT) {
      int tileX = startX;
      int column = startColumn;
      while (tileX < Game.WIDTH) {
        Cell cell = cell(column, row);
        if (cell != null) {
          Tile tile = cell.tile;
          if (tile != null) {
            Image image = tile.image;
            if (image != null) {
              canvas.draw(image, tileX, tileY, cell.flip.x, cell.flip.y);
            }
          }
        }
        tileX += tileSize.width;
        column++;
      }
      tileY += tileSize.height;
      row++;
    }
  }

  @Override
  public String toString() {
    return String.format("tileMap(%s), tile(%s))", size, tileSize);
  }
}
