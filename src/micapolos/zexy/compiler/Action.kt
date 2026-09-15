package micapolos.zexy.compiler

import micapolos.zexy.runtime.*
import micapolos.zexy.indexed.Action as IndexedAction

fun Compiler.evaluator(indexed: IndexedAction): Evaluator<Action> =
  runtime(indexed).let { ObjectEvaluator{it} }

fun Compiler.runtime(indexed: IndexedAction): Action =
  when (indexed) {
    IndexedAction.Empty -> emptyAction
    is IndexedAction.Set<*> -> setAction(state, indexed.variable.typedIndex, indexed.variable.index, evaluator(indexed.value))
    is IndexedAction.Bind<*> -> bindAction(state, indexed.variable.typedIndex, indexed.variable.index, evaluator(indexed.value))
    is IndexedAction.Select -> selectAction(intEvaluator(indexed.index), indexed.actions.map { evaluator(it) })
    is IndexedAction.Sequence -> sequenceAction(indexed.actions.map { evaluator(it) })
    is IndexedAction.Log -> logAction(indexed.label, evaluator(indexed.value))
  }