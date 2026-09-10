package micapolos.zexy3.compiler

import micapolos.tata8.Game
import micapolos.zexy3.indexed.Integer
import micapolos.zexy3.indexed.Number
import micapolos.zexy3.runtime.Animation
import micapolos.zexy3.runtime.DoubleEvaluator
import micapolos.zexy3.runtime.noAnimation
import kotlin.math.*

fun Compiler.animation(number: Number): Animation =
  when (number) {
    is Number.Apply0 -> noAnimation
    is Number.Apply1 -> noAnimation
    is Number.Apply2 -> noAnimation
    is Number.Constant -> noAnimation
    is Number.FromInteger -> noAnimation
  }

fun Compiler.numberEvaluator(number: Number): DoubleEvaluator =
  when (number) {
    is Number.Constant -> DoubleEvaluator { number.d }

    is Number.Apply0 ->
      when (number.op) {
        Number.Op0.FRAME_TIME -> DoubleEvaluator { 1.0 / 60 }
        Number.Op0.MOUSE_X -> DoubleEvaluator { Game.mouse.position.x.toDouble() }
        Number.Op0.MOUSE_Y -> DoubleEvaluator { Game.mouse.position.y.toDouble() }
      }

    is Number.Apply1 -> {
      val evaluator = numberEvaluator(number.n as Number)
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
      val lhs = numberEvaluator(number.lhs as Number)
      val rhs = numberEvaluator(number.rhs as Number)
      when (number.op) {
        Number.Op2.ADD -> DoubleEvaluator { lhs.eval() + rhs.eval() }
        Number.Op2.SUB -> DoubleEvaluator { lhs.eval() - rhs.eval() }
        Number.Op2.MUL -> DoubleEvaluator { lhs.eval() * rhs.eval() }
      }
    }

    is Number.FromInteger -> {
      val i = integerEvaluator(number.i as Integer)
      DoubleEvaluator { i.eval().toDouble() }
    }
  }

fun main() {
  println(
    Compiler(
      Number::class,
      IntArray(10),
      DoubleArray(10),
      Array(10) { null },
    )
      .numberEvaluator(
        Number.Apply2(
          Number.Op2.ADD,
          Number.Constant(12.0),
          Number.FromInteger(
            Integer.Apply0(Integer.Op0.SCREEN_WIDTH)))).eval())
}