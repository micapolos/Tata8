package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Void
import micapolos.zexy3.model.Void as ModelVoid

fun Indexer.indexed(v: ModelVoid): Void =
  when (v) {
    ModelVoid.Empty -> Void.Empty
    ModelVoid.Pause -> Void.Pause
    is ModelVoid.Set<*> -> Void.Set(indexed(v.variable), indexed(v.value))
  }