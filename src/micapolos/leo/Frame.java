package micapolos.leo;

public final class Frame extends Component {
  public final Value<Image> image;
  public final Number duration;

  public Frame(Value<Image> image, Number duration) {
    this.image = image;
    this.duration = duration;
  }

  @Override
  void addRunners() {
    image.addRunnersOnce();
    duration.addRunnersOnce();
  }
}
