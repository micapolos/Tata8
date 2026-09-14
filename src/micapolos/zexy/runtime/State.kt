package micapolos.zexy.runtime

class State(
  val intArray: IntArray = IntArray(0),
  val doubleArray: DoubleArray = DoubleArray(0),
  val objectArray: Array<Any?> = arrayOfNulls(0),
  val animatedArray: Array<Animated<*>?> = arrayOfNulls(0),
  val evaluatorArray: Array<Evaluator<*>?> = arrayOfNulls(0),
)

fun State.setInt(typedIndex: Int, index: Int, value: Int) {
  animatedArray[index] = null
  evaluatorArray[index] = null
  intArray[typedIndex] = value
}

fun State.setDouble(typedIndex: Int, index: Int, value: Double) {
  animatedArray[index] = null
  evaluatorArray[index] = null
  doubleArray[typedIndex] = value
}

fun <T> State.setObject(typedIndex: Int, index: Int, value: T) {
  animatedArray[index] = null
  evaluatorArray[index] = null
  objectArray[typedIndex] = value
}

fun State.bindInt(index: Int, animated: Animated<Int>) {
  animatedArray[index] = animated
  evaluatorArray[index] = null
}

fun State.bindDouble(index: Int, animated: Animated<Double>) {
  animatedArray[index] = animated
  evaluatorArray[index] = null
}

fun <T> State.bindObject(typedIndex: Int, index: Int, animated: Animated<T>) {
  animatedArray[index] = animated
  evaluatorArray[index] = null
  objectArray[typedIndex] = null
}

fun State.bind(index: Int, evaluator: IntEvaluator) {
  animatedArray[index] = null
  evaluatorArray[index] = evaluator
}

fun State.bind(index: Int, evaluator: DoubleEvaluator) {
  animatedArray[index] = null
  evaluatorArray[index] = evaluator
}

fun State.bind(typedIndex: Int, index: Int, evaluator: ObjectEvaluator<*>) {
  animatedArray[index] = null
  evaluatorArray[index] = evaluator
  objectArray[typedIndex] = null
}

fun State.evaluatorOrNull(index: Int): Evaluator<*>? =
  (animatedArray[index]?.evaluator ?: evaluatorArray[index]) as Evaluator<*>?

fun State.getInt(typedIndex: Int, index: Int): Int =
  evaluatorOrNull(index).let {
    if (it != null) {
      it.evalInt()
    } else {
      intArray[typedIndex]
    }
  }

fun State.getDouble(typedIndex: Int, index: Int): Double =
  evaluatorOrNull(index).let {
    if (it != null) {
      it.evalDouble()
    } else {
      doubleArray[typedIndex]
    }
  }

fun State.getObject(typedIndex: Int, index: Int): Any? =
  evaluatorOrNull(index).let {
    if (it != null) {
      it.evalObject()
    } else {
      objectArray[typedIndex]
    }
  }
