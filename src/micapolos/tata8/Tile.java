package micapolos.tata8;

public final class Tile {
  public final Image image;
  public final boolean[] flags = new boolean[8];

  Tile(Image image) {
    this.image = image;
  }
}
