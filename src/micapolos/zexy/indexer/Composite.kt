package micapolos.zexy.indexer

import micapolos.zexy.indexed.Composite
import micapolos.zexy.model.Composite as ModelComposite

val ModelComposite.indexed: Composite get() =
  when (this) {
    ModelComposite.NORMAL -> Composite.NORMAL
    ModelComposite.SOFT_LIGHT -> Composite.SOFT_LIGHT
    ModelComposite.MULTIPLY -> Composite.MULTIPLY
  }