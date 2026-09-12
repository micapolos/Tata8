package micapolos.zexy.compiler

import micapolos.zexy.indexed.Composite
import micapolos.tata8.Composite as TataComposite

val Composite.tata8 get() =
  when (this) {
    Composite.NORMAL -> TataComposite.NORMAL
    Composite.SOFT_LIGHT -> TataComposite.SOFT_LIGHT
    Composite.MULTIPLY -> TataComposite.MULTIPLY
  }