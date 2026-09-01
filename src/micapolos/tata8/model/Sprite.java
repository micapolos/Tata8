package micapolos.tata8.model;

import static micapolos.Blocks.ifNotNull;
import static micapolos.tata8.model.Anchor.anchor;
import static micapolos.tata8.model.Anchor.topLeftAnchor;
import static micapolos.tata8.model.Clip.*;
import static micapolos.tata8.model.Flip.noFlip;
import static micapolos.tata8.model.Image.image;
import static micapolos.tata8.model.Position.position;
import static micapolos.tata8.model.Value.nullValue;

public final class Sprite extends Component {
  final micapolos.tata8.Sprite state;
  public final Value<Image> image;
  public final Anchor anchor;
  public final Position position;
  public final Flip flip;

  Sprite(Clip clip,
         Value<Image> image,
         Anchor anchor,
         Position position,
         Flip flip,
         micapolos.tata8.Sprite state) {
    super(clip);
    this.state = state;
    this.image = image;
    this.anchor = anchor;
    this.position = position;
    this.flip = flip;
  }

  @Override
  void addClips() {
    image.maybeAddClips();
    anchor.maybeAddClips();
    position.maybeAddClips();
    flip.maybeAddClips();

    Game.add(new Clip() {
      @Override
      void start() {
      }

      @Override
      float step(float seconds) {
        state.image = ifNotNull(image.get(), it -> it.state);
        state.anchor.set((float) anchor.x.get(), (float) anchor.y.get());
        state.position.set((float) position.x.get(), (float) position.y.get());
        state.flip.set(flip.x.get(), flip.y.get());
        return seconds;
      }
    });
  }

  public static Sprite newSprite() {
    micapolos.tata8.Sprite state = micapolos.tata8.Game.newSprite();
    return new Sprite(Clip.emptyClip, nullValue(), topLeftAnchor, position(0, 0), noFlip, state);
  }

  public Sprite with(Clip clip) {
    return new Sprite(clip, image, anchor, position, flip, state);
  }

  public Sprite with(Value<Image> image) {
    return new Sprite(clip, image, anchor, position, flip, state);
  }

  public Sprite with(Anchor anchor) {
    return new Sprite(clip, image, anchor, position, flip, state);
  }

  public Sprite with(Position position) {
    return new Sprite(clip, image, anchor, position, flip, state);
  }

  public Sprite with(Flip flip) {
    return new Sprite(clip, image, anchor, position, flip, state);
  }

  @Override
  public String toString() {
    return String.format("sprite(%s)", position);
  }

  static void main() {
    Number x = Number.newNumber(160);

    newSprite()
      .with(image(Game.class, "depressedChicken.png").sliceVertically(8).get(0))
      .with(anchor(16, 16))
      .with(position(x, 128))
      .with(frame(1/60f, x.add(1)).repeat())
      .show();
  }
}
