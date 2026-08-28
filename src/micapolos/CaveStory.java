package micapolos;

import micapolos.tata8.*;
import micapolos.tata8.Math;

class CaveStory {
  static final int GROUND = 0;
  static final TileSet tileSet = Game.loadTileSet(CaveStory.class, "tilemap.png");
  static final Image image = Game.loadImage(CaveStory.class, "quote.png");

  final Position cameraPosition = new Position();
  final Speed speed = new Speed();
  boolean isJumping;

  final Sprite sprite = Game.newSprite();

  CaveStory() {
    tileSet.tile(0, 0).flags[GROUND] = true;
    tileSet.tile(1, 0).flags[GROUND] = true;
    tileSet.tile(2, 0).flags[GROUND] = true;

    TileMap tileMap = Game.background.tileMap;

    tileMap.draw9Patch(1, 12, 10, 13, tileSet, 0, 0);
    tileMap.draw9Patch(13, 14, 17, 15, tileSet, 0, 0);
    tileMap.draw9Patch(4, 18, 14, 20, tileSet, 0, 0);
    tileMap.draw9Patch(22, 17, 25, 18, tileSet, 0, 0);
    tileMap.draw9Patch(30, 14, 42, 15, tileSet, 0, 0);
    tileMap.draw9Patch(45, 11, 48, 12, tileSet, 0, 0);
    tileMap.draw9Patch(35, 8, 40, 9, tileSet, 0, 0);
    tileMap.draw9Patch(45, 5, 50, 6, tileSet, 0, 0);
    tileMap.draw9Patch(35, 2, 39, 3, tileSet, 0, 0);
    tileMap.draw9Patch(28, -1, 29, 8, tileSet, 0, 0);
    tileMap.draw9Patch(22, -4, 23, 8, tileSet, 0, 0);
    tileMap.draw9Patch(16, -7, 17, -6, tileSet, 0, 0);
    tileMap.draw9Patch(20, -10, 22, -9, tileSet, 0, 0);
    tileMap.draw9Patch(17, -12, 18, -11, tileSet, 0, 0);

    sprite.position.set(100, 100);
    sprite.anchor.set(16, 32);
    sprite.image = image;

    cameraPosition.set(sprite.position.x - 160, sprite.position.y - 128);
    Game.camera.position.set(cameraPosition);

    String title = "Cave Story by micapolos";
    int titleWidth = Font.system.width(title);
    Game.foreground.canvas.draw(title, (Game.size.width - titleWidth) / 2, 4, Color.YELLOW, Font.system, true);
  }

  void update() {
    if (Game.keys.up.didPress() && !isJumping) {
      speed.y = -5f;
      isJumping = true;
    }

    sprite.position.y += speed.y;
    sprite.position.x += speed.x;

    int cellX = (int) Math.floor(sprite.position.x / 16);
    int cellY = (int) Math.floor(sprite.position.y / 16);
    boolean hasGround = Game.background.tileMap.cell(cellX, cellY).tile.flags[GROUND];
    if (!hasGround) {
      speed.y = Math.clamp(speed.y + 0.25f, -8, 8);
    } else if (speed.y >= 0) {
      sprite.position.y = cellY * 16;
      speed.y = 0;
      isJumping = false;
    }
    cameraPosition.set(sprite.position.x - 160, sprite.position.y - 128);
    Game.camera.position.setElastic(cameraPosition);

    if (Game.keys.left.isPressed()) {
      speed.x = Math.elastic(speed.x, -3);
      sprite.flip.x = true;
    }

    if (Game.keys.right.isPressed()) {
      speed.x = Math.elastic(speed.x, 3);
      sprite.flip.x = false;
    }

    if (!Game.keys.right.isPressed() && !Game.keys.left.isPressed()) {
      if (!isJumping) {
        speed.x = Math.elastic(speed.x, 0, Math.ELASTIC_FACTOR * 2);
      } else {
        speed.x *= 0.75f;
      }
    }
  }

  static void main() {
    var caveStory = new CaveStory();
    Game.onUpdate = caveStory::update;
    Game.start();
  }
}
