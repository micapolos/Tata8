package micapolos.tata8;

import micapolos.awt.*;

import java.awt.image.BufferedImageOp;

public enum Shader {
  CRT_SCANLINE(new CrtSimpleScanlineOp(), 3),
  CRT_GRID(new CrtGridScanlineOp(), 3),
  CRT_PHOSPHOR(new CrtPhosphorMatrixOp(), 3),
  CRT_BLOCK(new GamePixelMatrixOp(), 6),
  CRT_LIGHT_POINT(new LightPointMatrixOp(), 6);

  final BufferedImageOp op;
  final int pixelSize;

  Shader(BufferedImageOp op, int pixelSize) {
    this.op = op;
    this.pixelSize = pixelSize;
  }

  public static Shader nextOf(Shader shaderOrNull) {
    return shaderOrNull == null
      ? Shader.values()[0]
      : shaderOrNull.ordinal() + 1 == Shader.values().length
      ? null
      : Shader.values()[shaderOrNull.ordinal() + 1];
  }
}
