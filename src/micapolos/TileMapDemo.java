package micapolos;

import micapolos.tata8.*;

public class TileMapDemo {
  static final TileSet tileSet = Game.loadTileSet(TileMapDemo.class, "tilemap.png");
  static final Image image = Game.loadImage(TileMapDemo.class, "quote.png");
  static final Sprite sprite = Game.newSprite();

  static {
    TileMap tileMap = Game.background.tileMap;
    tileMap.draw9Patch(1, 12, 15, 15, tileSet, 0, 0);
    sprite.position.set(100, 161);
    sprite.image = image;
  }

  static void update() {
    Game.log(tileSet);
    Game.log(Game.background);
    Game.log(Game.foreground);
    Game.log(Game.camera);
    Game.log(sprite);

    if (Game.keys.left.isPressed()) {
      Game.camera.position.x -= 2;
      sprite.position.x -= 2;
    }

    if (Game.keys.right.isPressed()) {
      Game.camera.position.x += 2;
      sprite.position.x += 2;
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
