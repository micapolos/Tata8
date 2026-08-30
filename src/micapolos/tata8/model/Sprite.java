package micapolos.tata8.model;

import micapolos.tata8.Image;

import static micapolos.tata8.Game.loadImage;
import static micapolos.tata8.model.Anchor.anchor;
import static micapolos.tata8.model.Flip.flip;
import static micapolos.tata8.model.Game.add;
import static micapolos.tata8.model.Value.value;

public final class Sprite implements Showable {
  final micapolos.tata8.Sprite state;

  public final Value<Image> image = value();
  public final Position position = Position.position();
  public final Anchor anchor = anchor();
  public final Flip flip = flip();

  Sprite(micapolos.tata8.Sprite state) {
    this.state = state;
  }

  public static Sprite sprite(Image image) {
    Sprite sprite = new Sprite(micapolos.tata8.Game.newSprite());
    sprite.image.init(image);
    add(sprite::sync);
    return sprite;
  }

  public static Sprite sprite() {
    Sprite sprite = new Sprite(micapolos.tata8.Game.newSprite());
    add(sprite::sync);
    return sprite;
  }

  void sync() {
    state.image = image.get();
    state.anchor.set((float) anchor.x.get(), (float) anchor.y.get());
    state.position.set((float) position.x.get(), (float) position.y.get());
    state.flip.set(flip.x.get(), flip.y.get());
  }

  @Override
  public void show() {
    Game.show();
  }

  static void main() {
    Sprite sprite = sprite();
    Image image = loadImage(Game.class, "depressedChicken.png").sliceVertically(8)[0];
    sprite.image.init(image);
    sprite.anchor.init(16, 16);
    sprite.position.init(160, 128);
    sprite.show();
  }
}
