package micapolos.tata8.model;

public final class Frame {
  public final Value<Image> image;
  public final Number duration;

  public Frame(Value<Image> image, Number duration) {
    this.image = image;
    this.duration = duration;
  }
}
