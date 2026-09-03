package micapolos.tata8;

import micapolos.awt.MameImageOp;

import java.awt.image.BufferedImageOp;

public enum Shader {
  MAME(new MameImageOp());

  final BufferedImageOp op;

  Shader(BufferedImageOp op) {
    this.op = op;
  }
}
