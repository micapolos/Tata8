package micapolos.zexy.evaluator

import micapolos.zexy.compiler.toInt

fun interface IntEvaluator : Evaluator<Int> {
  enum class Op2 {
    ADD,
    SUB,
    MUL,
    DIV,
    REM,
    EQ,
    CMP
  }

  fun evaluate(state: State): Int

  override fun evaluateInt(state: State): Int = evaluate(state)

  fun constant(i: Int) = IntEvaluator { i }

  fun apply2(op: Op2, lhs: IntEvaluator, rhs: IntEvaluator): IntEvaluator =
    when (op) {
      Op2.ADD -> IntEvaluator { state -> lhs.evaluate(state) + lhs.evaluate(state) }
      Op2.SUB -> IntEvaluator { state -> lhs.evaluate(state) - lhs.evaluate(state) }
      Op2.MUL -> IntEvaluator { state -> lhs.evaluate(state) * lhs.evaluate(state) }
      Op2.DIV -> IntEvaluator { state -> lhs.evaluate(state) / lhs.evaluate(state) }
      Op2.REM -> IntEvaluator { state -> lhs.evaluate(state) % lhs.evaluate(state) }
      Op2.EQ -> IntEvaluator { state -> (lhs.evaluate(state) == lhs.evaluate(state)).toInt() }
      Op2.CMP -> IntEvaluator { state -> lhs.evaluate(state).compareTo(lhs.evaluate(state)) }
    }
}

