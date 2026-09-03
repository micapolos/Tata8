package micapolos.zexy;

import micapolos.tata8.Canvas;

import static micapolos.Leo.*;
import static micapolos.zexy.Anchor.*;
import static micapolos.zexy.Camera.*;
import static micapolos.zexy.Animation.*;
import static micapolos.zexy.Flip.*;
import static micapolos.zexy.Image.*;
import static micapolos.zexy.Number.*;
import static micapolos.zexy.ParallaxRatio.*;
import static micapolos.zexy.Position.*;
import static micapolos.zexy.Scale.*;
import static micapolos.zexy.Value.*;

public final class Sprite extends Component implements Drawable {
  public final Value<Image> image;
  public final Anchor anchor;
  public final Position position;
  public final Flip flip;
  public final Scale scale;
  public final ParallaxRatio parallaxRatio;

  Sprite(Animation animation,
         Value<Image> image,
         Anchor anchor,
         Position position,
         Flip flip,
         Scale scale,
         ParallaxRatio parallaxRatio) {
    super(animation);
    this.image = image;
    this.anchor = anchor;
    this.position = position;
    this.flip = flip;
    this.scale = scale;
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
    return new Sprite(Animation.noAnimation, nullValue(), topLeftAnchor, position(0, 0), noFlip, noScale, parallaxRatio(1));
  }

  public Sprite with(Animation animation) {
    return new Sprite(animation, image, anchor, position, flip, scale, parallaxRatio);
  }

  public Sprite with(Value<Image> image) {
    return new Sprite(animation, image, anchor, position, flip, scale, parallaxRatio);
  }

  public Sprite with(Anchor anchor) {
    return new Sprite(animation, image, anchor, position, flip, scale, parallaxRatio);
  }

  public Sprite with(Position position) {
    return new Sprite(animation, image, anchor, position, flip, scale, parallaxRatio);
  }

  public Sprite with(Flip flip) {
    return new Sprite(animation, image, anchor, position, flip, scale, parallaxRatio);
  }

  public Sprite with(Scale scale) {
    return new Sprite(animation, image, anchor, position, flip, scale, parallaxRatio);
  }

  public Sprite with(ParallaxRatio parallaxRatio) {
    return new Sprite(animation, image, anchor, position, flip, scale, parallaxRatio);
  }

  @Override
  public String toString() {
    return leo("sprite", image, anchor, position, flip, scale, parallaxRatio);
  }

  @Override
  public void drawOn(Canvas canvas) {
    canvas.draw(
      image.get().state,
      (float) anchor.x.get(), (float) anchor.y.get(),
      (float) ParallaxRatio.screen(position.x.get(), camera.anchor.x.get(), camera.position.x.get(), parallaxRatio.number.get()),
      (float) ParallaxRatio.screen(position.y.get(), camera.anchor.y.get(), camera.position.y.get(), 1),
      flip.x.get(), flip.y.get(),
      (float) scale.x.get(), (float) scale.y.get(),
      0);
  }

  static void main() {
    Number x = number(n -> instant(n.set(160)).then(frame(1/60f, n.add(1)).repeat()));

    newSprite()
      .with(image(Game.class, "depressedChicken.png").sliceVertically(8).get(0))
      .with(anchor(16, 16))
      .with(position(x, 128))
      .with(parallaxRatio(1))
      .show();
  }
}
