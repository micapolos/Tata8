package micapolos.zexy.evaluator

sealed interface Evaluator<out T> {
  fun evaluateInt(state: State): Int = error("Not int")
  fun evaluateDouble(state: State): Double = error("Not int")
  fun evaluateAny(state: State): T = error("Not any")

  fun interface DoubleEvaluator : Evaluator<Double> {
    fun evaluate(): Double
    override fun evaluateDouble(state: State): Double = evaluate()
  }

  fun interface AnyEvaluator<out T> : Evaluator<T> {
    fun evaluate(): T
    override fun evaluateAny(state: State): T = evaluate()
  }
}