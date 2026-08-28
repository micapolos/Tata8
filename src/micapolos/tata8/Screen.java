package micapolos.tata8;

import java.awt.image.BufferedImageOp;

public final class Screen {
  public Shader shader;

  BufferedImageOp imageOp() {
    return shader != null ? shader.op : null;
  }
}
