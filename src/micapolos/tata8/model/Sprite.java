package micapolos.tata8.model;

import static micapolos.tata8.model.Anchor.anchorVariable;
import static micapolos.tata8.model.Flip.flipVariable;
import static micapolos.tata8.model.Game.add;
import static micapolos.tata8.model.Image.image;
import static micapolos.tata8.model.Position.positionVariable;
import static micapolos.tata8.model.Value.variable;

public final class Sprite implements Showable {
  final micapolos.tata8.Sprite state;

  public final Value<Image> image = variable();
  public final Position position = positionVariable();
  public final Anchor anchor = anchorVariable();
  public final Flip flip = flipVariable();

  Sprite(micapolos.tata8.Sprite state) {
    this.state = state;
  }

  public static Sprite sprite() {
    Sprite sprite = new Sprite(micapolos.tata8.Game.newSprite());
    add(new Clip() {
      @Override
      void start() {

      }

      @Override
      float advance(float seconds) {
        sprite.sync();
        return 0;
      }
    });
    return sprite;
  }

  void sync() {
    state.image = image.get().state;
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
    Image image = image(Game.class, "depressedChicken.png").sliceVertically(8)[0];
    sprite.image.init(image);
    sprite.anchor.init(16, 16);
    sprite.position.init(160, 128);
    sprite.show();
  }
}
