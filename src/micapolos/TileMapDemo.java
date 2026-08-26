package micapolos;

import micapolos.tata8.Game;
import micapolos.tata8.Image;
import micapolos.tata8.Tile;

public class TileMapDemo {
  static final Image[] tileSetImages = Game.loadImage(TileMapDemo.class, "tilemap.png").slice(7, 11);

  static {
    Tile.Map tileMap = Game.backgroundTileMap;

    for (int row = 12; row < 16; row++) {
      for (int column = 0; column < 64; column++) {
        int tile = 0;
        if (column > 0) tile++;
        if (column == 63) tile++;
        if (row > 12) tile += 7;
        if (row == 15) tile += 7;
        if (column > 0 && column < 63 && row > 12 && row < 15 && Math.random() < 0.05f) {
          tile = 49;
        }
        tileMap.tile(row, column).image = tileSetImages[tile];
      }
    }
  }

  static void update() {
    if (Game.keys.left.isPressed()) {
      Game.backgroundTileMap.camera.x -= 2;
    }

    if (Game.keys.right.isPressed()) {
      Game.backgroundTileMap.camera.x += 2;
    }

    if (Game.keys.up.isPressed()) {
      Game.backgroundTileMap.camera.y -= 2;
    }

    if (Game.keys.down.isPressed()) {
      Game.backgroundTileMap.camera.y += 2;
    }
  }

  static void main() {
    Game.onUpdate = TileMapDemo::update;
    Game.start();
  }
}
