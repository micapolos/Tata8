package micapolos.zexy.compiler

import micapolos.zexy.indexed.*
import micapolos.zexy.indexed.Action
import micapolos.zexy.indexed.Animation
import micapolos.zexy.indexed.Drawing
import micapolos.zexy.indexed.Number
import micapolos.zexy.runtime.*

fun <T : Value<T>> Compiler.animated(value: Value<T>): Animated<*> =
  when (value) {
    is Variable -> animatedVariable(value)
    is Integer -> animatedInteger(value)
    is Number -> animatedNumber(value)
    is Text -> animatedText(value)
    is Color -> animatedColor(value)
    is Image -> animatedImage(value)
    is Font -> animatedFont(value)
    is Drawing -> animatedDrawing(value)
    is Void -> animatedVoid(value)
    is Action -> Animated(evaluator(value), instantAnimation)
    is Animation -> Animated(evaluator(value), instantAnimation)

    is Value.Logged -> {
      val animatedValue = animated(value.value)
      Animated(
        animatedValue.evaluator.logged(value.label),
        animatedValue.animation
      )
    }

    is Value.Select -> {
      val animatedIndex = animated(value.index)
      val indexEvaluator = animatedIndex.evaluator as IntEvaluator
      val animatedOptions = value.options.map { animated(it) }
      val animatedEvaluators = animatedOptions.map { it.evaluator }
      val selectAnimation = SelectAnimation(
        indexEvaluator,
        animatedOptions.map { it.animation }.toTypedArray()
      )
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
      Animated(evaluator, parallel(animatedIndex.animation, selectAnimation))
    }
  }
