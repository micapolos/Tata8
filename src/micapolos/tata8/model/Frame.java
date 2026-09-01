package micapolos.tata8.model;

public final class Frame {
  public final Value<Image> image;
  public final DoubleValue duration;

  public Frame(Value<Image> image, DoubleValue duration) {
    this.image = image;
    this.duration = duration;
  }
}
