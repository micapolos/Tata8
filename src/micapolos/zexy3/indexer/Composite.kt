package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Composite
import micapolos.zexy3.model.Composite as ModelComposite

fun indexed(composite: ModelComposite): Composite =
  when (composite) {
    ModelComposite.NORMAL -> Composite.NORMAL
    ModelComposite.SOFT_LIGHT -> Composite.SOFT_LIGHT
    ModelComposite.MULTIPLY -> Composite.MULTIPLY
  }