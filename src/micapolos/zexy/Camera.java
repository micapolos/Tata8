package micapolos.zexy;

import static micapolos.zexy.Number.seconds;
import static micapolos.zexy.Position.newPosition;
import static micapolos.Leo.*;

public final class Camera extends Component {
  public final Position position;

  Camera(Position position) {
    this.position = position;
  }

  public static final Camera camera = new Camera(newPosition());

  static {
    camera.addRunnersOnce();
  }

  @Override
  void addRunners() {
    position.addRunnersOnce();

    Game.add(new Runner() {
      @Override
      public void update(float seconds) {
        micapolos.tata8.Game.camera.position.set((float) position.x.get(), (float) position.y.get());
      }
    });
  }

  @Override
  public String toString() {
    return leo("camera", position);
  }

  static void main() {
    camera.position.x.setImmediately(seconds);
    camera.show();
  }
}
