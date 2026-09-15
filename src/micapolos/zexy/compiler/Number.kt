package micapolos.zexy.compiler

import micapolos.tata8.Game
import micapolos.zexy.indexed.Number
import micapolos.zexy.runtime.DoubleEvaluator
import micapolos.zexy.runtime.Evaluator
import kotlin.math.*

fun Compiler.numberEvaluator(number: Number): Evaluator<Double> =
  when (number) {
    is Number.Constant -> number.d.let { DoubleEvaluator { it } }

    is Number.Apply0 ->
      when (number.op) {
        Number.Op0.FRAME_TIME -> DoubleEvaluator { Game.FRAME_SECONDS.toDouble() }
      }

    is Number.Apply1 -> {
      val evaluator = doubleEvaluator(number.number)
      when (number.op) {
        Number.Op1.NEG -> DoubleEvaluator { -evaluator.eval() }
        Number.Op1.SIN -> DoubleEvaluator { sin(evaluator.eval()) }
        Number.Op1.COS -> DoubleEvaluator { cos(evaluator.eval()) }
        Number.Op1.ABS -> DoubleEvaluator { abs(evaluator.eval()) }
        Number.Op1.SQRT -> DoubleEvaluator { sqrt(evaluator.eval()) }
        Number.Op1.FLOOR -> DoubleEvaluator { floor(evaluator.eval()) }
        Number.Op1.CEIL -> DoubleEvaluator { ceil(evaluator.eval()) }
        Number.Op1.ROUND -> DoubleEvaluator { round(evaluator.eval()) }
        Number.Op1.FRACT -> DoubleEvaluator { evaluator.eval().let { it - floor(it) } }
      }
    }

    is Number.Apply2 -> {
      val lhs = doubleEvaluator(number.lhs)
      val rhs = doubleEvaluator(number.rhs)
      when (number.op) {
        Number.Op2.ADD -> DoubleEvaluator { lhs.eval() + rhs.eval() }
        Number.Op2.SUB -> DoubleEvaluator { lhs.eval() - rhs.eval() }
        Number.Op2.MUL -> DoubleEvaluator { lhs.eval() * rhs.eval() }
      }
    }

    is Number.FromInteger -> {
      val evaluator = intEvaluator(number.integer)
      DoubleEvaluator { evaluator.eval().toDouble() }
    }
  }
