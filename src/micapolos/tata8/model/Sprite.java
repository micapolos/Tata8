package micapolos.tata8.model;

import micapolos.tata8.Image;

public final class Sprite implements Showable {
  final micapolos.tata8.Sprite state;
  public final Value<Image> image = Value.newVariable();
  public final Position position = Position.newSlot();
  public final Number angle = Number.newVariable();

  Sprite(micapolos.tata8.Sprite state) {
    this.state = state;
  }

  void sync() {
    state.image = image.get();
    state.angle = (float) angle.get();
    state.position.set((float) position.x.get(), (float) position.y.get());
  }
}
