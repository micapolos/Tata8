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

    is Value.StartWhen -> TODO()//evaluator(value.value)
    is Value.Stretch -> TODO()//evaluator(value.value)
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

fun <T : Value<T>> Compiler.evaluator(value: Value<T>): Evaluator<*> =
  when (value) {
    is Variable -> variableEvaluator(value)
    is Integer -> integerEvaluator(value)
    is Number -> numberEvaluator(value)
    is Text -> textEvaluator(value)
    is Color -> colorEvaluator(value)
    is Image -> imageEvaluator(value)
    is Font -> fontEvaluator(value)
    is Drawing -> drawingEvaluator(value)
    is Void -> voidEvaluator(value)

    is Value.Logged -> evaluator(value.value).logged(value.label)
    is Value.RunWhile -> TODO()//evaluator(value.value)
    is Value.Select -> TODO()
    is Value.Sequence -> TODO()
    is Value.StartWhen -> TODO()//evaluator(value.value)
    is Value.Stretch -> TODO()//evaluator(value.value)
    is Value.Stateful -> TODO()
    is Value.Race -> TODO()
    is Value.Frame -> animated(value).evaluator
  }

fun Compiler.intEvaluator(value: Value<Integer>): IntEvaluator =
  animated(value).evaluator as IntEvaluator

fun Compiler.doubleEvaluator(value: Value<Number>): DoubleEvaluator =
  animated(value).evaluator as DoubleEvaluator

fun <T : Value<T>> Compiler.objectEvaluator(value: Value<T>): ObjectEvaluator<*> =
  animated(value).evaluator as ObjectEvaluator<*>

fun <T : Value<T>> Compiler.animation(value: Value<T>): Animation =
  when (value) {
    is Variable -> variableAnimation(value)
    is Integer -> integerAnimation(value)
    is Number -> numberAnimation(value)
    is Color -> colorAnimation(value)
    is Text -> textAnimation(value)
    is Image -> imageAnimation(value)
    is Font -> fontAnimation(value)
    is Drawing -> drawingAnimation(value)
    is Void -> voidAnimation(value)

    is Value.Logged -> animation(value.value)
    is Value.RunWhile ->
      animation(value.value)
        .runWhileNotZero(intEvaluator(value.condition))

    is Value.Select -> TODO()
    is Value.Sequence -> TODO()

    is Value.StartWhen ->
      animation(value.value)
        .startWhenNotZero(intEvaluator(value.condition))

    is Value.Stretch ->
      animation(value.value)
        .stretch(doubleEvaluator(value.factor))

    is Value.Stateful -> TODO()
    is Value.Race -> TODO()
    is Value.Frame -> animated(value).animation
  }
