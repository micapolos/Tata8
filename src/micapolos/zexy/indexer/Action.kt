package micapolos.zexy.indexer

import micapolos.zexy.indexed.Action
import micapolos.zexy.indexed.Action.*
import micapolos.zexy.model.Action as ModelAction

fun Indexer.indexed(model: ModelAction): Action =
  when (model) {
    ModelAction.Empty -> Action.Empty
    is ModelAction.Set<*> -> Set(indexedVariable(model.variable), indexed(model.value))
    is ModelAction.Bind<*> -> Bind(indexedVariable(model.variable), indexed(model.value))
    is ModelAction.Sequence -> Sequence(model.actions.map { indexed(it) })
    is ModelAction.Select -> Select(indexed(model.index), model.actions.map { indexed(it) })
    is ModelAction.Log -> Log(model.label, indexed(model.value))
  }