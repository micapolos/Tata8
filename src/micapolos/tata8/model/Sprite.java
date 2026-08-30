package micapolos.tata8.model;

import micapolos.tata8.Image;

import static micapolos.tata8.Game.loadImage;
import static micapolos.tata8.model.Game.*;

public final class Sprite implements Showable {
  final micapolos.tata8.Sprite state;
  public final Value<Image> image = Value.variable();
  public final Position position = Position.variable();
  public final Anchor anchor = Anchor.variable();
  public final Flip flip = Flip.variable();
  public final Number angle = Number.variable();

  Sprite(micapolos.tata8.Sprite state) {
    this.state = state;
  }

  void sync() {
    state.image = image.get();
    state.position.set((float) position.x.get(), (float) position.y.get());
    state.anchor.set((float) anchor.x.get(), (float) anchor.y.get());
    state.flip.set(flip.x.get(), flip.y.get());
    state.angle = (float) angle.get();
  }

  @Override
  public void show() {
    Game.show();
  }

  static void main() {
    Sprite sprite = newSprite();
    Image image = loadImage(Game.class, "depressedChicken.png").sliceVertically(8)[0];
    sprite.image.set(image);
    sprite.position.set(seconds.times(60), constant(128));
    sprite.show();
  }
}
