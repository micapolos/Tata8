package micapolos.zexy.runtime

class State(
  val intArray: IntArray = IntArray(0),
  val doubleArray: DoubleArray = DoubleArray(0),
  val objectArray: Array<Any?> = arrayOfNulls(0),
  val evaluatorArray: Array<Evaluator<*>?> = arrayOfNulls(0),
)

fun State.setInt(typedIndex: Int, index: Int, value: Int) {
  evaluatorArray[index] = null
  intArray[typedIndex] = value
}

fun State.setDouble(typedIndex: Int, index: Int, value: Double) {
  evaluatorArray[index] = null
  doubleArray[typedIndex] = value
}

fun <T> State.setObject(typedIndex: Int, index: Int, value: T) {
  evaluatorArray[index] = null
  objectArray[typedIndex] = value
}

fun State.bind(index: Int, evaluator: IntEvaluator) {
  evaluatorArray[index] = evaluator
}

fun State.bind(index: Int, evaluator: DoubleEvaluator) {
  evaluatorArray[index] = evaluator
}

fun State.bind(typedIndex: Int, index: Int, evaluator: ObjectEvaluator<*>) {
  evaluatorArray[index] = evaluator
  objectArray[typedIndex] = null
}

fun State.evaluatorOrNull(index: Int): Evaluator<*>? =
  evaluatorArray[index]

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
