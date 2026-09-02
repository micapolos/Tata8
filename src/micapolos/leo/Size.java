package micapolos.leo;

public final class Size extends Component {
  public final Number width;
  public final Number height;

  Size(Number width, Number height) {
    this.width = width;
    this.height = height;
  }

  @Override
  void addRunners() {
    width.addRunnersOnce();
    height.addRunnersOnce();
  }
}
