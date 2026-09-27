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
