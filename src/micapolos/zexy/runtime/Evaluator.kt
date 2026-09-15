package micapolos.zexy.runtime

import micapolos.tata8.Game

sealed interface Evaluator<out T> {
  fun evalUnit(): Unit

  fun evalBoxed(): T

  fun evalInt(): Int = error("Not int")
  fun evalDouble(): Double = error("Not double")
  fun evalObject(): T = evalBoxed()

  fun logged(label: String?) =
    when (this) {
      is IntEvaluator -> loggedInt(label)
      is DoubleEvaluator -> loggedDouble(label)
      is ObjectEvaluator<*> -> loggedObject(label)
    } as Evaluator<T>
}

fun interface IntEvaluator : Evaluator<Int> {
  fun eval(): Int

  override fun evalUnit() {
    eval()
  }

  override fun evalBoxed() = eval()

  override fun evalInt() = eval()

  fun loggedInt(label: String?): IntEvaluator =
    IntEvaluator {
      eval().also { Game.log(label, it) }
    }
}

fun interface DoubleEvaluator : Evaluator<Double> {
  fun eval(): Double

  override fun evalUnit() {
    eval()
  }

  override fun evalBoxed() = eval()

  override fun evalDouble() = eval()

  fun loggedDouble(label: String?): DoubleEvaluator =
    DoubleEvaluator {
      eval().also { Game.log(label, it) }
    }
}

fun interface ObjectEvaluator<T> : Evaluator<T> {
  fun eval(): T

  override fun evalUnit() {
    eval()
  }

  override fun evalBoxed() = eval()

  fun loggedObject(label: String?): ObjectEvaluator<T> =
    ObjectEvaluator {
      eval().also { Game.log(label, it) }
    }
}

fun parallelEvaluator(evaluators: List<Evaluator<*>>): Evaluator<Unit> =
  ObjectEvaluator {
    evaluators.forEach { it.evalUnit() }
  }

fun <T> statefulEvaluator(stateEvaluator: Evaluator<*>, evaluator: Evaluator<T>): Evaluator<T> =
  when (evaluator) {
    is DoubleEvaluator -> DoubleEvaluator {
      evaluator.eval().also { stateEvaluator.evalUnit() }
    } as Evaluator<T>

    is IntEvaluator -> IntEvaluator {
      evaluator.eval().also { stateEvaluator.evalUnit() }
    } as Evaluator<T>

    is ObjectEvaluator<T> -> ObjectEvaluator {
      evaluator.eval().also { stateEvaluator.evalUnit() }
    }
  }

fun <T> selectEvaluator(indexEvaluator: IntEvaluator, vararg evaluators: Evaluator<T>): Evaluator<T> =
  when (evaluators.first()) {
    is IntEvaluator -> IntEvaluator {
      (evaluators[indexEvaluator.eval()] as IntEvaluator).eval()
    } as Evaluator<T>

    is DoubleEvaluator -> DoubleEvaluator {
      (evaluators[indexEvaluator.eval()] as DoubleEvaluator).eval()
    } as Evaluator<T>

    is ObjectEvaluator<*> -> ObjectEvaluator {
      (evaluators[indexEvaluator.eval()] as ObjectEvaluator<T>).eval()
    }
  }

fun <T> captureAnimation(state: State, evaluator: Evaluator<T>, typedIndex: Int, index: Int): Animation = run {
  when (evaluator) {
    is IntEvaluator ->
      actionAnimation {
        state.intArray[typedIndex] = evaluator.eval()
      }

    is DoubleEvaluator ->
      actionAnimation {
        state.doubleArray[typedIndex] = evaluator.eval()
      }

    is ObjectEvaluator<T> ->
      actionAnimation {
        state.objectArray[typedIndex] = evaluator.eval()
      }
  }
}