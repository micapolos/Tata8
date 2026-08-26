package micapolos.tata8;

public final class Tile {
  public Image image;
  public boolean[] flags = new boolean[8];

  Tile() {}

  public static final class Map {
    public final FinalSize tileSize;
    public final FloatVector camera = new FloatVector();
    public final Tile[][] tiles;

    Map(FinalSize tileSize, Tile[][] tiles) {
      this.tileSize = tileSize;
      this.tiles = tiles;
      for (Tile[] row : tiles) {
        for (int i = 0; i < row.length; i++) {
          row[i] = new Tile();
        }
      }
    }

    static Map create(int rowCount, int columnCount, int tileWidth, int tileHeight) {
      Tile[][] tiles = new Tile[rowCount][columnCount];
      return new Map(new FinalSize(tileWidth, tileHeight), tiles);
    }

    public Tile tile(int row, int column) {
      return tiles[row][column];
    }

    Tile tileOrNull(int row, int column) {
      Tile[] tileRow = row >= 0 && row < tiles.length ? tiles[row] : null;
      if (tileRow == null) {
        return null;
      }
      return column >= 0 && column < tileRow.length ? tileRow[column] : null;
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
          Tile tile = tileOrNull(row, column);
          if (tile != null) {
            Image image = tile.image;
            if (image != null) {
              canvas.draw(image, x, y);
            }
          }
          x += tileSize.width;
          column++;
        }
        y += tileSize.height;
        row++;
      }
    }
  }
}
