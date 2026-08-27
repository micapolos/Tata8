package micapolos;

import micapolos.tata8.Game;
import micapolos.tata8.TileMap;
import micapolos.tata8.TileSet;

public class TileMapDemo {
  static final TileSet tileSet = Game.loadTileSet(TileMapDemo.class, "tilemap.png", 7, 11);

  static {
    TileMap tileMap = Game.BACKGROUND_TILE_MAP;

    for (int row = 12; row < 16; row++) {
      for (int column = 0; column < 64; column++) {
        boolean isBox = column > 0 && column < 63 && row > 12 && row < 15 && Math.random() < 0.1f;
        tileMap.cell(row, column).tile =
            isBox
                ? tileSet.tile(0, 7)
                : tileSet.tile(column == 0 ? 0 : column == 63 ? 2 : 1, row == 12 ? 0 : row == 15 ? 2 : 1);
      }
    }
  }

  static void update() {
    if (Game.keys.left.isPressed()) {
      Game.BACKGROUND_TILE_MAP.camera.x -= 2;
    }

    if (Game.keys.right.isPressed()) {
      Game.BACKGROUND_TILE_MAP.camera.x += 2;
    }

    if (Game.keys.up.isPressed()) {
      Game.BACKGROUND_TILE_MAP.camera.y -= 2;
    }

    if (Game.keys.down.isPressed()) {
      Game.BACKGROUND_TILE_MAP.camera.y += 2;
    }
  }

  static void main() {
    Game.onUpdate = TileMapDemo::update;
    Game.start();
  }
}
