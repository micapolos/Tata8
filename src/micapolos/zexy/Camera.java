package micapolos.zexy;

import static micapolos.Leo.*;
import static micapolos.zexy.Number.*;
import static micapolos.zexy.Position.*;

public final class Camera extends Component {
  public final Position position;

  Camera(Position position) {
    this.position = position;
  }

  public static final Camera camera = new Camera(newPosition(-160, -128));

  static {
    camera.addRunnersOnce();
  }

  @Override
  void addRunners() {
    position.addRunnersOnce();
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
