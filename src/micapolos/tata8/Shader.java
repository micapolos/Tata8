package micapolos.tata8;

import micapolos.awt.CrtGridScanlineOp;
import micapolos.awt.CrtPhosphorMatrixOp;
import micapolos.awt.CrtSimpleScanlineOp;

import java.awt.image.BufferedImageOp;

public enum Shader {
  CRT_PHOSPHOR(new CrtPhosphorMatrixOp()),
  CRT_SCANLINE(new CrtSimpleScanlineOp()),
  CRT_GRID(new CrtGridScanlineOp());

  final BufferedImageOp op;

  Shader(BufferedImageOp op) {
    this.op = op;
  }

  public static Shader nextOf(Shader shaderOrNull) {
    return shaderOrNull == null
      ? Shader.values()[0]
      : shaderOrNull.ordinal() + 1 == Shader.values().length
      ? null
      : Shader.values()[shaderOrNull.ordinal() + 1];
  }
}
