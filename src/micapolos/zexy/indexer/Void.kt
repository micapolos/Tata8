package micapolos.zexy.indexer

import micapolos.zexy.indexed.Void
import micapolos.zexy.model.Void as ModelVoid

fun Indexer.indexedVoid(model: ModelVoid): Void =
  when (model) {
    is ModelVoid.Empty -> Void.Empty
  }