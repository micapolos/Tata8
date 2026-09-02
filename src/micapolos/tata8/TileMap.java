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

  public void draw(TilePatch patch, int x, int y) {
    for (int dx = 0; dx < patch.size.width; dx++) {
      for (int dy = 0; dy < patch.size.height; dy++) {
        cell(x + dx, y + dy).tile =
            patch.tileSet.tile(patch.position.x + dx, patch.position.y + dy);
      }
    }
  }

  public void draw(Tile9Patch patch, int x, int y, int width, int height) {
    draw9Patch(
        x, y,
        width, height,
        patch.tileSet,
        patch.position.x, patch.position.y,
        patch.size.width, patch.size.height,
        patch.cornerSize.width, patch.cornerSize.height);
  }

  public void draw9Patch(
      int x, int y,
      int width, int height,
      TileSet tileSet,
      int patchX, int patchY,
      int patchWidth, int patchHeight,
      int cornerWidth, int cornerHeight) {
    for (int dx = 0; dx < width; dx++) {
      for (int dy = 0; dy < height; dy++) {
        cell(x + dx, y + dy).tile = tileSet.tile(
            patchX + threePatchCoord(dx, width, patchWidth, cornerWidth),
            patchY + threePatchCoord(dy, height, patchHeight, cornerHeight));
      }
    }
  }

  private int threePatchCoord(int x, int width, int patchWidth, int cornerWidth) {
    return x < cornerWidth
        ? x
        : x > width - patchWidth + cornerWidth
            ? x - width + patchWidth
            : cornerWidth;
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
    x = Math.round(x);
    y = Math.round(y);
    int startRow = (int) Math.floor(-y / tileSize.height);
    int startY = (int) Math.round(startRow * tileSize.height + y);
    int startColumn = (int) Math.floor(-x / tileSize.width);
    int startX = (int) Math.round(startColumn * tileSize.width + x);
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
