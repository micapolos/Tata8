package micapolos.tata8;

import java.lang.Math;

public final class IntFrame {
  public final IntPosition position = new IntPosition();
  public final IntSize size = new IntSize();

  public void set(IntFrame frame) {
    position.set(frame.position);
    size.set(frame.size);
  }

  public void set(int x, int y, int width, int height) {
    position.set(x, y);
    size.set(width, height);
  }

  public void intersect(int x, int y, int width, int height) {
    var x1 = Math.max(position.x, x);
    var y1 = Math.max(position.y, y);
    var x2 = Math.min(position.x + size.width, x + width);
    var y2 = Math.min(position.y + size.height, y + height);
    position.set(x1, y1);
    size.set(x2 - x1, y2 - y1);
  }
}