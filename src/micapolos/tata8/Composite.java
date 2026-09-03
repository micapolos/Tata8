package micapolos.tata8;

import micapolos.awt.MultiplyComposite;
import micapolos.awt.SoftLightComposite;

import java.awt.*;

public enum Composite {
  NORMAL(AlphaComposite.SrcOver),
  SOFT_LIGHT(SoftLightComposite.INSTANCE),
  MULTIPLY(MultiplyComposite.INSTANCE);

  final java.awt.Composite awt;

  Composite(java.awt.Composite awt) {
    this.awt = awt;
  }
}
