package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Void
import micapolos.zexy3.model.Void as ModelVoid

fun Indexer.indexed(model: ModelVoid): Void =
  when (model) {
    ModelVoid.Empty -> Void.Empty
    ModelVoid.Pause -> Void.Pause
    is ModelVoid.Set<*> -> Void.Set(indexed(model.variable), indexed(model.value))
  }