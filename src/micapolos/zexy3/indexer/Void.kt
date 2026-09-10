package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Void
import micapolos.zexy3.model.Void as ModelVoid

fun Indexer.indexedVoid(model: ModelVoid): Void =
  when (model) {
    is ModelVoid.Pause -> Void.Pause(indexed(model.seconds))
    is ModelVoid.Set<*> -> Void.Set(indexedVariable(model.variable), indexed(model.value))
    is ModelVoid.Parallel -> Void.Parallel(model.values.map { indexed(it) })
    is ModelVoid.Race -> Void.Race(model.values.map { indexed(it) })
  }