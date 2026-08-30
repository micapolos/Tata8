package micapolos.tata8.model;

import micapolos.tata8.Image;

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
}
