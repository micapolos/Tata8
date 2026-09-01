package micapolos.tata8.model;

import static micapolos.Blocks.ifNotNull;
import static micapolos.tata8.model.Image.load;

public final class Sprite extends Component {
  final micapolos.tata8.Sprite state;

  public final Value<Image> image = Value.newVariable();
  public final Position position = Position.newVariable();
  public final Anchor anchor = Anchor.newVariable();
  public final Flip flip = Flip.newVariable();

  Sprite(micapolos.tata8.Sprite state) {
    this.state = state;
  }

  @Override
  void addClips() {
    image.maybeAddClips();
    position.maybeAddClips();
    anchor.maybeAddClips();
    flip.maybeAddClips();
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
    return newSprite(Value.with(image));
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
  public String toString() {
    return String.format("sprite(%s)", position);
  }

  @Override
  public void show() {
    Game.show();
  }

  static void main() {
    Image image = load(Game.class, "depressedChicken.png").sliceVertically(8)[0];
    Sprite sprite = newSprite();
    sprite.image.init(image);
    sprite.anchor.init(16, 16);
    sprite.position.init(160, 128);
    sprite.show();
  }
}
