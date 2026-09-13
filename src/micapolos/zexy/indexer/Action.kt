package micapolos.zexy.indexer

import micapolos.zexy.indexed.Action
import micapolos.zexy.model.Action as ModelAction

fun Indexer.indexed(model: ModelAction): Action =
  when (model) {
    ModelAction.Empty -> Action.Empty
    is ModelAction.Capture<*> -> Action.Capture(indexedVariable(model.variable), indexed(model.value))
    is ModelAction.Set<*> -> Action.Set(indexedVariable(model.variable), indexed(model.value))
    is ModelAction.Sequence -> Action.Sequence(model.actions.map { indexed(it) })
    is ModelAction.Select -> Action.Select(indexed(model.index), model.actions.map { indexed(it) })
    is ModelAction.Draw -> Action.Draw(indexed(model.drawing))
  }