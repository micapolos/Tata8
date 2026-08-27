package micapolos.tata8;

public final class TileSet {
  public final FinalSize size;
  final Tile[][] tiles;

  TileSet(FinalSize size, Tile[][] tiles) {
    this.size = size;
    this.tiles = tiles;
  }

  public Tile tile(int x, int y) {
    return Arrays.get(tiles, x, y);
  }

  public void set(int x, int y, Tile tile) {
    Arrays.set(tiles, x, y, tile);
  }

  static TileSet slice(Image image) {
    int width = image.size.width / 16;
    int height = image.size.height / 16;
    Tile[][] tiles = new Tile[width][height];
    Image[] images = image.slice(width, height);
    int index = 0;
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        tiles[x][y] = new Tile(images[index++]);
      }
    }
    return new TileSet(new FinalSize(width, height), tiles);
  }

  @Override
  public String toString() {
    return String.format("tileSet(%s)", size);
  }
}
