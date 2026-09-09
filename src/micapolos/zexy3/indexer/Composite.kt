package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Composite
import micapolos.zexy3.model.Composite as ModelComposite

val ModelComposite.indexed: Composite get() =
  when (this) {
    ModelComposite.NORMAL -> Composite.NORMAL
    ModelComposite.SOFT_LIGHT -> Composite.SOFT_LIGHT
    ModelComposite.MULTIPLY -> Composite.MULTIPLY
  }