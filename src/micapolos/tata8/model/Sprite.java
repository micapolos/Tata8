package micapolos.tata8.model;

import static micapolos.Blocks.ifNotNull;
import static micapolos.tata8.model.Anchor.anchorVariable;
import static micapolos.tata8.model.Flip.flipVariable;
import static micapolos.tata8.model.Game.add;
import static micapolos.tata8.model.Image.image;
import static micapolos.tata8.model.Value.value;
import static micapolos.tata8.model.Value.variable;

public final class Sprite implements Showable {
  final micapolos.tata8.Sprite state;

  public final Value<Image> image = variable();
  public final Position position = Position.variable();
  public final Anchor anchor = anchorVariable();
  public final Flip flip = flipVariable();

  Sprite(micapolos.tata8.Sprite state) {
    this.state = state;
  }

  public static Sprite newSprite() {
    Sprite sprite = new Sprite(micapolos.tata8.Game.newSprite());
    Game.add(seconds -> {
      sprite.sync();
      return seconds;
    });
    return sprite;
  }

  public static Sprite newSprite(Image image) {
    return newSprite(value(image));
  }

  public static Sprite newSprite(Value<Image> image) {
    Sprite sprite = newSprite();
    sprite.image.init(image);
    return sprite;
  }

  public static Sprite newSprite(Image image, Anchor anchor) {
    Sprite sprite = newSprite();
    sprite.image.init(image);
    sprite.anchor.init(anchor);
    return sprite;
  }

  void sync() {
    state.image = ifNotNull(image.get(), it -> it.state);
    state.anchor.set((float) anchor.x.get(), (float) anchor.y.get());
    state.position.set((float) position.x.get(), (float) position.y.get());
    state.flip.set(flip.x.get(), flip.y.get());
  }

  @Override
  public void show() {
    Game.show();
  }

  static void main() {
    Sprite sprite = newSprite();
    Image image = image(Game.class, "depressedChicken.png").sliceVertically(8)[0];
    sprite.image.init(image);
    sprite.anchor.setImmediately(16, 16);
    sprite.position.init(160, 128);
    sprite.show();
  }
}
