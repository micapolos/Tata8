package micapolos;

import micapolos.tata8.Game;
import micapolos.tata8.TileMap;
import micapolos.tata8.TileSet;

public class TileMapDemo {
  static final TileSet tileSet = Game.loadTileSet(TileMapDemo.class, "tilemap.png");

  static {
    TileMap tileMap = Game.backgroundTileMap;

    tileMap.draw9Patch(1, 12, 15, 15, tileSet, 0, 0);
  }

  static void update() {
    Game.log(tileSet);
    Game.log(Game.backgroundTileMap);

    if (Game.keys.left.isPressed()) {
      Game.camera.position.x -= 2;
    }

    if (Game.keys.right.isPressed()) {
      Game.camera.position.x += 2;
    }

    if (Game.keys.up.isPressed()) {
      Game.camera.position.y -= 2;
    }

    if (Game.keys.down.isPressed()) {
      Game.camera.position.y += 2;
    }
  }

  static void main() {
    Game.onUpdate = TileMapDemo::update;
    Game.start();
  }
}
