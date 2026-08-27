package micapolos;

import micapolos.tata8.*;
import micapolos.tata8.Math;

public class TileMapDemo {
  static final int GROUND = 0;
  static float dy;
  static float dx;
  static boolean isJumping;
  static final Position cameraPosition = new Position();

  static final TileSet tileSet = Game.loadTileSet(TileMapDemo.class, "tilemap.png");
  static final Image image = Game.loadImage(TileMapDemo.class, "quote.png");
  static final Sprite sprite = Game.newSprite();

  static {
    tileSet.tile(0, 0).flags[GROUND] = true;
    tileSet.tile(1, 0).flags[GROUND] = true;
    tileSet.tile(2, 0).flags[GROUND] = true;

    TileMap tileMap = Game.background.tileMap;
    tileMap.draw9Patch(1, 12, 10, 13, tileSet, 0, 0);
    tileMap.draw9Patch(13, 14, 17, 15, tileSet, 0, 0);
    tileMap.draw9Patch(4, 18, 16, 20, tileSet, 0, 0);
    tileMap.draw9Patch(20, 17, 25, 18, tileSet, 0, 0);
    sprite.position.set(100, 100);
    sprite.anchor.set(16, 32);
    sprite.image = image;

    cameraPosition.set(sprite.position.x - 160, sprite.position.y - 128);
    Game.camera.position.set(cameraPosition);
  }

  static void update() {
    Game.log("dx", dx);
    Game.log("dy", dy);
    Game.log("jump", isJumping);

    if (Game.keys.up.didPress() && !isJumping) {
      dy = -5f;
      isJumping = true;
    }

    sprite.position.y += dy;
    sprite.position.x += dx;

    int cellX = (int) Math.floor(sprite.position.x / 16);
    int cellY = (int) Math.floor(sprite.position.y / 16);
    boolean hasGround = Game.background.tileMap.cell(cellX, cellY).tile.flags[GROUND];
    if (!hasGround) {
      dy = Math.clamp(dy + 0.25f, -10, 10);
    } else if (dy >= 0) {
      sprite.position.y = cellY * 16;
      dy = 0;
      isJumping = false;
    }
    cameraPosition.set(sprite.position.x - 160, sprite.position.y - 128);
    Game.camera.position.setElastic(cameraPosition);

    if (Game.keys.left.isPressed()) {
      dx = Math.clamp(dx - 0.125f, -3, 3);
      sprite.flip.x = true;
    }

    if (Game.keys.right.isPressed()) {
      dx = Math.clamp(dx + 0.125f, -3, 3);
      sprite.flip.x = false;
    }

    if (!Game.keys.right.isPressed() && !Game.keys.left.isPressed()) {
      if (!isJumping) {
        dx = 0;
      } else {
        dx *= 0.75f;
      }
    }
  }

  static void main() {
    Game.onUpdate = TileMapDemo::update;
    Game.start();
  }
}
