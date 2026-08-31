package micapolos.tata8.model;

public final class Frame extends Child {
  public final Value<Image> image;
  public final Number duration;

  public Frame(Value<Image> image, Number duration) {
    super(image, duration);
    this.image = image;
    this.duration = duration;
  }
}
