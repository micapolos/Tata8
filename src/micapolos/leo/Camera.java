package micapolos.leo;

import static micapolos.leo.Number.seconds;
import static micapolos.leo.Position.newPosition;

public final class Camera extends Component {
  public final Position position;

  Camera(Position position) {
    this.position = position;
  }

  public static final Camera camera = new Camera(newPosition());

  static {
    camera.maybeAddClips();
  }

  @Override
  void addClips() {
    position.maybeAddClips();

    Game.add(new Clip() {
      @Override
      void start() {

      }

      @Override
      float step(float seconds) {
        micapolos.tata8.Game.camera.position.set((float) position.x.get(), (float) position.y.get());
        return seconds;
      }
    });
  }

  @Override
  public String toString() {
    return String.format("camera(%s)", position);
  }

  static void main() {
    camera.position.x.setImmediately(seconds);
    camera.show();
  }
}
