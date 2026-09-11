package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.*
import micapolos.zexy3.indexed.Drawing
import micapolos.zexy3.indexed.Number
import micapolos.zexy3.runtime.*
import kotlin.math.min

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

    is Value.Logged -> {
      val animatedValue = animated(value.value)
      Animated(
        animatedValue.evaluator.logged(value.label),
        animatedValue.animation
      )
    }

    is Value.RunWhile -> {
      val animatedCondition = animated(value.condition)
      val animatedValue = animated(value.condition)
      Animated(
        animatedValue.evaluator,
        race(
          listOf(
            animatedCondition.animation,
            animatedValue.animation.runWhileNotZero(animatedCondition.evaluator as IntEvaluator)
          )
        )
      )
    }

    is Value.Select -> {
      val animatedInteger = animated(value.index)
      val animatedOptions = value.options.map { animated(it) }
      val animatedEvaluators = animatedOptions.map { it.evaluator }
      val animation = SelectAnimation(
        animatedInteger.evaluator as IntEvaluator,
        animatedOptions.map { it.animation }.toTypedArray()
      )
      val evaluator = when (animatedOptions.first().evaluator) {
        is IntEvaluator -> IntEvaluator {
          (animatedEvaluators[animation.selectedIndex] as IntEvaluator).eval()
        }

        is DoubleEvaluator -> DoubleEvaluator {
          (animatedEvaluators[animation.selectedIndex] as DoubleEvaluator).eval()
        }

        is ObjectEvaluator<*> -> ObjectEvaluator {
          (animatedEvaluators[animation.selectedIndex] as ObjectEvaluator).eval()
        }
      }
      Animated(evaluator, animation)
    }

    is Value.Sequence -> {
      val animatedValues = value.values.map { animated(it) }
      val animation = SequenceAnimation(animatedValues.map { it.animation })
      val evaluators = animatedValues.map { it.evaluator }
      Animated(
        when (evaluators.first()) {
          is IntEvaluator -> IntEvaluator {
            (evaluators[min(animation.index, evaluators.size - 1)] as IntEvaluator).eval()
          }

          is DoubleEvaluator -> DoubleEvaluator {
            (evaluators[min(animation.index, evaluators.size - 1)] as DoubleEvaluator).eval()
          }

          is ObjectEvaluator<*> -> ObjectEvaluator {
            (evaluators[min(animation.index, evaluators.size - 1)] as ObjectEvaluator).eval()
          }
        },
        animation
      )
    }

    is Value.StartWhen -> {
      val animatedCondition = animated(value.condition)
      val animatedValue = animated(value.value)
      Animated(
        animatedValue.evaluator,
        parallel(
          animatedCondition.animation,
          animation(value.value).startWhenNotZero(animatedCondition.evaluator as IntEvaluator)
        )
      )
    }

    is Value.Stretch -> {
      val animatedValue = animated(value.value)
      val animatedFactor = animated(value.factor)
      Animated(
        animatedValue.evaluator,
        parallel(
          animatedFactor.animation,
          animatedValue.animation.stretch(animatedFactor.evaluator as DoubleEvaluator)
        )
      )
    }

    is Value.Stateful -> {
      val animatedState = animated(value.state)
      val animatedValue = animated(value.value)
      Animated(
        statefulEvaluator(animatedState.evaluator, animatedValue.evaluator),
        parallel(listOf(animatedState.animation, animatedValue.animation))
      )
    }

    is Value.Race -> TODO()
    is Value.Frame -> {
      val animated = animated(value.value)
      val evaluator = animated.evaluator
      val animation = animated.animation
      Animated(evaluator, frameAnimation(animation))
    }
  }

fun <T : Value<T>> Compiler.evaluator(value: Value<T>): Evaluator<*> = TODO()
fun <T : Value<T>> Compiler.animation(value: Value<T>): Animation = TODO()
