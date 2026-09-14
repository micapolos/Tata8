package micapolos.zexy.runtime

import micapolos.tata8.Game

fun interface Action {
  fun execute()
}

val emptyAction = Action {}

fun <T> bindAction(state: State, typedIndex: Int, index: Int, evaluator: Evaluator<T>): Action =
  when (evaluator) {
    is IntEvaluator ->
      Action {
        state.bind(index, evaluator)
      }

    is DoubleEvaluator -> {
      Action {
        state.bind(index, evaluator)
      }
    }

    is ObjectEvaluator<T> -> {
      Action {
        state.bind(typedIndex, index, evaluator)
      }
    }
  }

fun <T> setAction(state: State, typedIndex: Int, index: Int, evaluator: Evaluator<T>): Action =
  when (evaluator) {
    is IntEvaluator -> Action {
      state.setInt(typedIndex, index, evaluator.eval())
    }

    is DoubleEvaluator ->
      Action {
        state.setDouble(typedIndex, index, evaluator.eval())
      }

    is ObjectEvaluator<*> ->
      Action {
        state.setObject(typedIndex, index, evaluator.eval())
      }
  }

fun sequenceAction(actions: List<Action>): Action =
  Action {
    actions.forEach(Action::execute)
  }

fun selectAction(indexEvaluator: IntEvaluator, actions: List<Action>): Action =
  Action {
    actions.forEach(Action::execute)
  }

fun <T> logAction(label: String?, evaluator: Evaluator<T>): Action =
  Action {
    Game.log(label, evaluator.evalBoxed())
  }

