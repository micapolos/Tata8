package micapolos.zexy.compiler

import micapolos.tata8.Game
import micapolos.zexy.indexed.Number
import micapolos.zexy.runtime.*
import kotlin.math.*

fun Compiler.animatedNumber(number: Number): Animated<Double> =
  when (number) {
    is Number.Constant -> {
      val d = number.d
      Animated(DoubleEvaluator { d }, instantAnimation)
    }

    is Number.Apply0 -> {
      when (number.op) {
        Number.Op0.FRAME_TIME -> Animated(DoubleEvaluator { Game.FRAME_SECONDS.toDouble() }, infiniteAnimation)
      }
    }

    is Number.Apply1 -> {
      val animatedNumber = animated(number.number)
      val evaluator = animatedNumber.evaluator as DoubleEvaluator
      Animated(
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
        },
        animatedNumber.animation
      )
    }

    is Number.Apply2 -> {
      val animatedLhs = animated(number.lhs)
      val animatedRhs = animated(number.rhs)
      val lhs = animatedLhs.evaluator as DoubleEvaluator
      val rhs = animatedRhs.evaluator as DoubleEvaluator
      Animated(
        when (number.op) {
          Number.Op2.ADD -> DoubleEvaluator { lhs.eval() + rhs.eval() }
          Number.Op2.SUB -> DoubleEvaluator { lhs.eval() - rhs.eval() }
          Number.Op2.MUL -> DoubleEvaluator { lhs.eval() * rhs.eval() }
        },
        parallel(animatedLhs.animation, animatedRhs.animation)
      )
    }

    is Number.FromInteger -> {
      val animatedInt = animated(number.integer)
      val evaluator = animatedInt.evaluator as IntEvaluator
      Animated(
        DoubleEvaluator { evaluator.eval().toDouble() },
        animatedInt.animation
      )
    }
  }
