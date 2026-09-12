package micapolos.zexy.indexer

import micapolos.zexy.indexed.Void
import micapolos.zexy.model.Void as ModelVoid

fun Indexer.indexedVoid(model: ModelVoid): Void =
  when (model) {
    is ModelVoid.Empty -> Void.Empty
    is ModelVoid.Pause -> Void.Pause(indexed(model.seconds))
    is ModelVoid.Set<*> -> Void.Set(indexedVariable(model.variable), indexed(model.value))
    is ModelVoid.Capture<*> -> Void.Capture(indexedVariable(model.variable), indexed(model.value))
    is ModelVoid.Parallel -> Void.Parallel(model.values.map { indexed(it) })
    is ModelVoid.Race -> Void.Race(model.values.map { indexed(it) })
  }