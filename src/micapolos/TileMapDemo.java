package micapolos;

import micapolos.tata8.Game;
import micapolos.tata8.TileMap;
import micapolos.tata8.TileSet;

public class TileMapDemo {
  static final TileSet tileSet = Game.loadTileSet(TileMapDemo.class, "tilemap.png");

  static {
    TileMap tileMap = Game.backTileMap;

    for (int x = 0; x < 64; x++) {
    for (int y = 12; y < 16; y++) {
        boolean isBox = x > 0 && x < 63 && y > 12 && y < 15 && Math.random() < 0.1f;
        tileMap.cell(x, y).tile =
            isBox
                ? tileSet.tile(0, 7)
                : tileSet.tile(x == 0 ? 0 : x == 63 ? 2 : 1, y == 12 ? 0 : y == 15 ? 2 : 1);
      }
    }
  }

  static void update() {
    Game.log(tileSet);
    Game.log(Game.backTileMap);

    if (Game.keys.left.isPressed()) {
      Game.backTileMap.camera.x -= 2;
    }

    if (Game.keys.right.isPressed()) {
      Game.backTileMap.camera.x += 2;
    }

    if (Game.keys.up.isPressed()) {
      Game.backTileMap.camera.y -= 2;
    }

    if (Game.keys.down.isPressed()) {
      Game.backTileMap.camera.y += 2;
    }
  }

  static void main() {
    Game.onUpdate = TileMapDemo::update;
    Game.start();
  }
}
