package micapolos.tata8;

public final class Tile {
  public Image image;
  public final boolean[] flags = new boolean[8];

  Tile() {}

  Tile(Image image) {
    this.image = image;
  }
}
