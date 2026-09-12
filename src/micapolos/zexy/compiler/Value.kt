package micapolos.zexy.compiler

import micapolos.zexy.indexed.*
import micapolos.zexy.indexed.Drawing
import micapolos.zexy.indexed.Number
import micapolos.zexy.runtime.*
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
      val animatedValue = animated(value.value)
      val conditionEvaluator = animatedCondition.evaluator as IntEvaluator
      Animated(
        animatedValue.evaluator,
        parallel(
          animatedCondition.animation,
          animatedValue.animation.runWhileNotZero { conditionEvaluator.eval() != 0 }
        )
      )
    }

    is Value.RepeatWhile -> {
      val animatedCondition = animated(value.condition)
      val animatedValue = animated(value.value)
      val conditionEvaluator = animatedCondition.evaluator as IntEvaluator
      Animated(
        animatedValue.evaluator,
        parallel(
          animatedCondition.animation,
          animatedValue.animation.repeatWhile { conditionEvaluator.eval() != 0 }
        )
      )
    }

    is Value.Select -> {
      val animatedInteger = animated(value.index)
      val animatedOptions = value.options.map { animated(it) }
      val animatedEvaluators = animatedOptions.map { it.evaluator }
      val selectAnimation = SelectAnimation(
        animatedInteger.evaluator as IntEvaluator,
        animatedOptions.map { it.animation }.toTypedArray()
      )
      val evaluator = when (animatedOptions.first().evaluator) {
        is IntEvaluator -> IntEvaluator {
          (animatedEvaluators[selectAnimation.selectedIndex] as IntEvaluator).eval()
        }

        is DoubleEvaluator -> DoubleEvaluator {
          (animatedEvaluators[selectAnimation.selectedIndex] as DoubleEvaluator).eval()
        }

        is ObjectEvaluator<*> -> ObjectEvaluator {
          (animatedEvaluators[selectAnimation.selectedIndex] as ObjectEvaluator).eval()
        }
      }
      Animated(evaluator, parallel(animatedInteger.animation, selectAnimation))
    }

    is Value.Sequence -> {
      val animatedValues = value.values.map { animated(it) }
      val sequenceAnimation = SequenceAnimation(animatedValues.map { it.animation })
      val evaluators = animatedValues.map { it.evaluator }
      Animated(
        when (evaluators.first()) {
          is IntEvaluator -> IntEvaluator {
            (evaluators[min(sequenceAnimation.index, evaluators.size - 1)] as IntEvaluator).eval()
          }

          is DoubleEvaluator -> DoubleEvaluator {
            (evaluators[min(sequenceAnimation.index, evaluators.size - 1)] as DoubleEvaluator).eval()
          }

          is ObjectEvaluator<*> -> ObjectEvaluator {
            (evaluators[min(sequenceAnimation.index, evaluators.size - 1)] as ObjectEvaluator).eval()
          }
        },
        sequenceAnimation
      )
    }

    is Value.StartWhen -> {
      val animatedCondition = animated(value.condition)
      val animatedValue = animated(value.value)
      Animated(
        animatedValue.evaluator,
        parallel(
          animatedCondition.animation,
          animatedValue.animation.startWhenNotZero(animatedCondition.evaluator as IntEvaluator)
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

    is Value.EveryFrame -> {
      val animated = animated(value.value)
      val evaluator = animated.evaluator
      val animation = animated.animation
      Animated(evaluator, everyFrameAnimation(animation))
    }

    is Value.NextFrame -> {
      val animated = animated(value.value)
      val evaluator = animated.evaluator
      val animation = animated.animation
      Animated(evaluator, nextFrameAnimation(animation))
    }
  }
