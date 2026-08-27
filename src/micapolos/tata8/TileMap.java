package micapolos.tata8;

public final class TileMap {
  final Cell[][] cells;
  public final FinalSize size;
  public final FloatVector camera = new FloatVector();

  TileMap(FinalSize tileSize, Cell[][] cells) {
    this.size = tileSize;
    this.cells = cells;
  }

  public Cell cell(int x, int y) {
    return Arrays.get(cells, x, y);
  }

  static TileMap create(int rowCount, int columnCount, int tileWidth, int tileHeight) {
    Cell[][] cells = new Cell[rowCount][columnCount];
    for (Cell[] cellRow : cells) {
      for (int i = 0; i < cellRow.length; i++) {
        cellRow[i] = new Cell();
      }
    }
    return new TileMap(new FinalSize(tileWidth, tileHeight), cells);
  }


  Cell cellOrNull(int row, int column) {
    Cell[] cellRow = row >= 0 && row < cells.length ? cells[row] : null;
    if (cellRow == null) {
      return null;
    }
    return column >= 0 && column < cellRow.length ? cellRow[column] : null;
  }

  void drawOn(Canvas canvas) {
    int startRow = (int) Math.floor(camera.y / size.height);
    int startY = (int) Math.round(startRow * size.height - camera.y);
    int startColumn = (int) Math.floor(camera.x / size.width);
    int startX = (int) Math.round(startColumn * size.width - camera.x);
    int y = startY;
    int row = startRow;
    while (y < Game.HEIGHT) {
      int x = startX;
      int column = startColumn;
      while (x < Game.WIDTH) {
        Cell cell = cellOrNull(row, column);
        if (cell != null) {
          Tile tile = cell.tile;
          if (tile != null) {
            Image image = tile.image;
            if (image != null) {
              canvas.draw(image, x, y, cell.flip.x, cell.flip.y);
            }
          }
        }
        x += size.width;
        column++;
      }
      y += size.height;
      row++;
    }
  }
}
