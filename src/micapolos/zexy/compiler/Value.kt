package micapolos.zexy.compiler

import micapolos.zexy.indexed.*
import micapolos.zexy.indexed.Number
import micapolos.zexy.runtime.DoubleEvaluator
import micapolos.zexy.runtime.Evaluator
import micapolos.zexy.runtime.IntEvaluator
import micapolos.zexy.runtime.ObjectEvaluator

fun Compiler.evaluator(value: Value): Evaluator<*> =
  when (value) {
    is Variable -> variableEvaluator(value)
    is Integer -> integerEvaluator(value)
    is Number -> numberEvaluator(value)
    is Text -> textEvaluator(value)
    is Color -> colorEvaluator(value)
    is Image -> imageEvaluator(value)
    is Font -> fontEvaluator(value)
    is Drawing -> drawingEvaluator(value)
    is Action -> evaluator(value)
    is Animation -> evaluator(value)

    is Value.Logged -> evaluator(value).logged(value.label)

    is Value.Select -> {
      val indexEvaluator = intEvaluator(value.index)
      val animatedEvaluators = value.options.map { evaluator(it) }
      when (animatedEvaluators.first()) {
        is IntEvaluator -> IntEvaluator {
          (animatedEvaluators[indexEvaluator.eval()] as IntEvaluator).eval()
        }

        is DoubleEvaluator -> DoubleEvaluator {
          (animatedEvaluators[indexEvaluator.eval()] as DoubleEvaluator).eval()
        }

        is ObjectEvaluator<*> -> ObjectEvaluator {
          (animatedEvaluators[indexEvaluator.eval()] as ObjectEvaluator).eval()
        }
      }
    }
  }
