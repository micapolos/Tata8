package micapolos.tata8.model;

import static micapolos.Blocks.*;
import static micapolos.tata8.model.Anchor.*;
import static micapolos.tata8.model.Clip.*;
import static micapolos.tata8.model.Depth.*;
import static micapolos.tata8.model.Flip.*;
import static micapolos.tata8.model.Image.*;
import static micapolos.tata8.model.Number.*;
import static micapolos.tata8.model.Position.*;
import static micapolos.tata8.model.Value.*;

public final class Sprite extends Component {
  final micapolos.tata8.Sprite state;
  public final Value<Image> image;
  public final Anchor anchor;
  public final Position position;
  public final Flip flip;
  public final Depth depth;

  Sprite(Clip clip,
         Value<Image> image,
         Anchor anchor,
         Position position,
         Flip flip,
         Depth depth,
         micapolos.tata8.Sprite state) {
    super(clip);
    this.state = state;
    this.image = image;
    this.anchor = anchor;
    this.position = position;
    this.flip = flip;
    this.depth = depth;
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
    return new Sprite(Clip.emptyClip, nullValue(), topLeftAnchor, position(0, 0), noFlip, depth(0), state);
  }

  public Sprite with(Clip clip) {
    return new Sprite(clip, image, anchor, position, flip, depth, state);
  }

  public Sprite with(Value<Image> image) {
    return new Sprite(clip, image, anchor, position, flip, depth, state);
  }

  public Sprite with(Anchor anchor) {
    return new Sprite(clip, image, anchor, position, flip, depth, state);
  }

  public Sprite with(Position position) {
    return new Sprite(clip, image, anchor, position, flip, depth, state);
  }

  public Sprite with(Flip flip) {
    return new Sprite(clip, image, anchor, position, flip, depth, state);
  }

  public Sprite with(Depth depth) {
    return new Sprite(clip, image, anchor, position, flip, depth, state);
  }

  @Override
  public String toString() {
    return String.format("sprite(%s)", position);
  }

  static void main() {
    Number x = newNumber(160);

    newSprite()
      .with(image(Game.class, "depressedChicken.png").sliceVertically(8).get(0))
      .with(anchor(16, 16))
      .with(position(x, 128))
      .with(frame(1/60f, x.add(1)).repeat())
      .with(depth(1))
      .show();
  }
}
