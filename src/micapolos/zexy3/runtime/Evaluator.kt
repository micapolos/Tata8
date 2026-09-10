package micapolos.zexy3.runtime

import micapolos.Leo
import micapolos.Leo.leo
import micapolos.tata8.Game

sealed interface Evaluator<out T> {
  fun evalUnit(): Unit

  fun evalBoxed(): T

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

abstract class StructEvaluator(val name: String, val fieldTypes: List<Type>): ObjectEvaluator<List<*>> {
  fun loggedStruct(label: String?): StructEvaluator =
    object : StructEvaluator(name, fieldTypes) {
      override fun eval(): List<*> = eval().also {
        Game.log(label, leo(name, it.map { eval() }.toTypedArray()))
      }
    }
}

fun parallelEvaluator(evaluators: List<Evaluator<*>>): Evaluator<Unit> =
  ObjectEvaluator {
    evaluators.forEach { it.evalUnit() }
  }

fun <T> statefulEvaluator(stateEvaluator: Evaluator<*>, evaluator: Evaluator<T>): Evaluator<T> =
  when (evaluator) {
    is DoubleEvaluator -> DoubleEvaluator {
      stateEvaluator.evalUnit()
      evaluator.eval()
    } as Evaluator<T>
    is IntEvaluator -> IntEvaluator {
      stateEvaluator.evalUnit()
      evaluator.eval()
    } as Evaluator<T>
    is ObjectEvaluator<T> -> ObjectEvaluator {
      stateEvaluator.evalUnit()
      evaluator.eval()
    }
  }
