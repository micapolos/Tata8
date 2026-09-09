package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Void
import micapolos.zexy3.model.Void as ModelVoid

fun Indexer.indexedVoid(modelVoid: ModelVoid): Void =
  when (modelVoid) {
    ModelVoid.Empty -> Void.Empty
    ModelVoid.Pause -> Void.Pause
    is ModelVoid.Set<*> -> Void.Set(indexedVariable(modelVoid.variable), indexed(modelVoid.value))
  }