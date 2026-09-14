package micapolos.zexy.runtime

import micapolos.tata8.Canvas

fun interface Action {
  fun execute()
}

val emptyAction = Action {}

fun <T> setAction(state: State, typedIndex: Int, index: Int, evaluator: Evaluator<T>): Action =
  when (evaluator) {
    is IntEvaluator ->
      Action {
        state.animatedArray[index] = null
        state.evaluatorArray[index] = evaluator
      }

    is DoubleEvaluator -> {
      Action {
        state.animatedArray[index] = null
        state.evaluatorArray[index] = evaluator
      }
    }

    is ObjectEvaluator<T> -> {
      Action {
        state.objectArray[typedIndex] = null  // avoids retention
        state.animatedArray[index] = null
        state.evaluatorArray[index] = evaluator
      }
    }
  }

fun <T> captureAction(state: State, typedIndex: Int, index: Int, evaluator: Evaluator<T>): Action =
  when (evaluator) {
    is IntEvaluator ->
      Action {
        state.intArray[typedIndex] = evaluator.eval()
        state.animatedArray[index] = null
        state.evaluatorArray[index] = null
      }

    is DoubleEvaluator ->
      Action {
        state.doubleArray[typedIndex] = evaluator.eval()
        state.animatedArray[index] = null
        state.evaluatorArray[index] = null
      }

    is ObjectEvaluator<*> ->
      Action {
        state.objectArray[typedIndex] = evaluator.eval()
        state.animatedArray[index] = null
        state.evaluatorArray[index] = null
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
