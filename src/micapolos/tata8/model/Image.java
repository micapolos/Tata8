package micapolos.tata8.model;

public final class Image implements Showable {
  final micapolos.tata8.Image state;

  Image(micapolos.tata8.Image state) {
    this.state = state;
  }

  public static Image image(Class<?> baseClass, String fileName) {
    return new Image(micapolos.tata8.Game.loadImage(baseClass, fileName));
  }

  public Image crop(int x, int y, int width, int height) {
    return new Image(state.crop(x, y, width, height));
  }

  public Image[] sliceVertically(int columnCount) {
    micapolos.tata8.Image[] states = state.sliceVertically(columnCount);
    Image[] images = new Image[states.length];
    for (int i = 0; i < states.length; i++) {
      images[i] = new Image(states[i]);
    }
    return images;
  }

  @Override
  public String toString() {
    return String.format("image(width: %s, height: %s)", state.size.width, state.size.height);
  }

  @Override
  public void show() {
    micapolos.tata8.Game.background.canvas.draw(state);
    micapolos.tata8.Game.start();
  }

  static void main() {
    image(Image.class, "depressedChicken.png").crop(0, 0, 32, 32).show();
  }
}
