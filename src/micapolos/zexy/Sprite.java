package micapolos.zexy;

import micapolos.tata8.Canvas;

import static micapolos.Leo.*;
import static micapolos.zexy.Anchor.*;
import static micapolos.zexy.Camera.*;
import static micapolos.zexy.Clip.*;
import static micapolos.zexy.Flip.*;
import static micapolos.zexy.Image.*;
import static micapolos.zexy.Number.*;
import static micapolos.zexy.ParallaxRatio.*;
import static micapolos.zexy.Position.*;
import static micapolos.zexy.Value.*;

public final class Sprite extends Component implements Drawable {
  public final Value<Image> image;
  public final Anchor anchor;
  public final Position position;
  public final Flip flip;
  public final ParallaxRatio parallaxRatio;

  Sprite(Clip clip,
         Value<Image> image,
         Anchor anchor,
         Position position,
         Flip flip,
         ParallaxRatio parallaxRatio) {
    super(clip);
    this.image = image;
    this.anchor = anchor;
    this.position = position;
    this.flip = flip;
    this.parallaxRatio = parallaxRatio;
  }

  @Override
  void addRunners() {
    image.addRunnersOnce();
    anchor.addRunnersOnce();
    position.addRunnersOnce();
    flip.addRunnersOnce();
    parallaxRatio.addRunnersOnce();
    camera.addRunnersOnce();
  }

  public static Sprite newSprite() {
    return new Sprite(Clip.emptyClip, nullValue(), topLeftAnchor, position(0, 0), noFlip, parallaxRatio(1));
  }

  public Sprite with(Clip clip) {
    return new Sprite(clip, image, anchor, position, flip, parallaxRatio);
  }

  public Sprite with(Value<Image> image) {
    return new Sprite(clip, image, anchor, position, flip, parallaxRatio);
  }

  public Sprite with(Anchor anchor) {
    return new Sprite(clip, image, anchor, position, flip, parallaxRatio);
  }

  public Sprite with(Position position) {
    return new Sprite(clip, image, anchor, position, flip, parallaxRatio);
  }

  public Sprite with(Flip flip) {
    return new Sprite(clip, image, anchor, position, flip, parallaxRatio);
  }

  public Sprite with(ParallaxRatio parallaxRatio) {
    return new Sprite(clip, image, anchor, position, flip, parallaxRatio);
  }

  @Override
  public String toString() {
    return leo("sprite", image, anchor, position, flip, parallaxRatio);
  }

  @Override
  public void drawOn(Canvas canvas) {
    canvas.draw(
      image.get().state,
      (float) anchor.x.get(), (float) anchor.y.get(),
      (float) -camera.position.x.get() + (float) position.x.get() * (float) parallaxRatio.number.get(),
      (float) -camera.position.y.get() + (float) position.y.get(),
      flip.x.get(), flip.y.get(),
      1, 1,
      0);
  }

  static void main() {
    Number x = newNumber(160);

    newSprite()
      .with(image(Game.class, "depressedChicken.png").sliceVertically(8).get(0))
      .with(anchor(16, 16))
      .with(position(x, 128))
      .with(frame(1/60f, x.add(1)).repeat())
      .with(parallaxRatio(1))
      .show();
  }
}
