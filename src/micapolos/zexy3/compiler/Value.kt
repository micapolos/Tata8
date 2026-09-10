package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.*
import micapolos.zexy3.indexed.Drawing
import micapolos.zexy3.indexed.Number
import micapolos.zexy3.runtime.*

fun <T: Value<T>> Compiler.animated(value: Value<T>): Animated<*> =
  Animated(evaluator(value), animation(value))

fun <T: Value<T>> Compiler.evaluator(value: Value<T>): Evaluator<*> =
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

    is Value.Logged<*> -> evaluator(value.value).logged(value.label)
    is Value.RunWhile<*> -> TODO()//evaluator(value.value)
    is Value.Select<*> -> TODO()
    is Value.Sequence<*> -> TODO()
    is Value.StartWhen<*> -> TODO()//evaluator(value.value)
    is Value.Stretch<*> -> TODO()//evaluator(value.value)
  }

fun Compiler.intEvaluator(value: Value<Integer>): IntEvaluator =
  animated(value).evaluator as IntEvaluator

fun Compiler.doubleEvaluator(value: Value<Number>): DoubleEvaluator =
  animated(value).evaluator as DoubleEvaluator

fun <T: Value<T>> Compiler.objectEvaluator(value: Value<T>): ObjectEvaluator<*> =
  animated(value).evaluator as ObjectEvaluator<*>

fun <T: Value<T>> Compiler.animation(value: Value<T>): Animation =
  when (value) {
    is Variable -> animation(value)
    is Integer -> animation(value)
    is Number -> animation(value)
    is Color -> animation(value)
    is Text -> animation(value)
    is Image -> animation(value)
    is Font -> animation(value)
    is Drawing -> animation(value)
    is Void -> animation(value)

    is Value.Logged -> noAnimation
    is Value.RunWhile<*> ->
      animation(value.value)
        .runWhileNotZero(intEvaluator(value.condition))
    is Value.Select<*> -> TODO()
    is Value.Sequence<*> ->
      sequence(value.values.map { animation(it) })
    is Value.StartWhen<*> ->
      animation(value.value)
        .startWhenNotZero(intEvaluator(value.condition))
    is Value.Stretch<*> ->
      animation(value.value)
        .stretch(doubleEvaluator(value.factor))
  }
