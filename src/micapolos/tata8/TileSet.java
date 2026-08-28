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

  public TilePatch newPatch(int x, int y, int width, int height) {
    TilePatch tilePatch = new TilePatch();
    tilePatch.tileSet = this;
    tilePatch.position.set(x, y);
    tilePatch.size.set(width, height);
    return tilePatch;
  }

  public Tile9Patch new9Patch(int x, int y) {
    return new9Patch(x, y, 3, 3, 1, 1);
  }

  public Tile9Patch new9Patch(int x, int y, int w, int h, int cw, int ch) {
    Tile9Patch tile9Patch = new Tile9Patch();
    tile9Patch.tileSet = this;
    tile9Patch.position.set(x, y);
    tile9Patch.size.set(w, h);
    tile9Patch.cornerSize.set(cw, ch);
    return tile9Patch;
  }

  static TileSet slice(Image image) {
    int width = image.size.width / 16;
    int height = image.size.height / 16;
    Tile[][] tiles = new Tile[width][height];
    Image[][] images = image.slice(width, height);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        tiles[x][y] = new Tile(images[x][y]);
      }
    }
    return new TileSet(new FinalSize(width, height), tiles);
  }

  @Override
  public String toString() {
    return String.format("tileSet(%s)", size);
  }
}
