package micapolos.zexy.compiler

import micapolos.zexy.indexed.*
import micapolos.zexy.indexed.Action
import micapolos.zexy.indexed.Animation
import micapolos.zexy.indexed.Drawing
import micapolos.zexy.indexed.Number
import micapolos.zexy.runtime.*

fun <T : Value<T>> Compiler.animated(value: Value<T>): Animated<*> =
  when (value) {
    is Variable -> Animated(variableEvaluator(value), ObjectEvaluator { instantAnimation })
    is Integer -> animatedInteger(value)
    is Number -> animatedNumber(value)
    is Text -> Animated(textEvaluator(value), ObjectEvaluator { instantAnimation })
    is Color -> Animated(colorEvaluator(value), ObjectEvaluator { instantAnimation })
    is Image -> Animated(imageEvaluator(value), ObjectEvaluator { instantAnimation })
    is Font -> Animated(fontEvaluator(value), ObjectEvaluator { instantAnimation })
    is Drawing -> Animated(drawingEvaluator(value), ObjectEvaluator { instantAnimation })
    is Action -> Animated(evaluator(value), ObjectEvaluator { instantAnimation })
    is Animation -> Animated(evaluator(value), ObjectEvaluator { instantAnimation })

    is Value.Logged -> Animated(evaluator(value).logged(value.label), ObjectEvaluator { instantAnimation })

    is Value.Select -> {
      val indexEvaluator = intEvaluator(value.index)
      val animatedOptions = value.options.map { animated(it) }
      val animatedEvaluators = animatedOptions.map { it.evaluator }
      val evaluator = when (animatedOptions.first().evaluator) {
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
      Animated(evaluator, ObjectEvaluator { instantAnimation })
    }
  }
