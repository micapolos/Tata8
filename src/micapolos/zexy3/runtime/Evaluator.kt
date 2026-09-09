package micapolos.zexy3.runtime

sealed interface Evaluator<out T>

fun interface IntEvaluator : Evaluator<Int> {
  fun eval(): Int
}

fun interface DoubleEvaluator : Evaluator<Double> {
  fun eval(): Double
}

fun interface ObjectEvaluator<T> : Evaluator<T> {
  fun eval(): T
}
