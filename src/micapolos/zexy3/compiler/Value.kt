package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.*
import micapolos.zexy3.indexed.Drawing
import micapolos.zexy3.indexed.Number
import micapolos.zexy3.runtime.*

fun <T: Value<T>, A> Compiler.animated(value: Value<T>): Animated<A> =
  Animated(evaluator(value), animation(value))

fun <T: Value<T>, R> Compiler.evaluator(value: Value<T>): Evaluator<R> =
  when (value) {
    is Variable -> evaluator(value)
    is Integer -> evaluator(value)
    is Number -> evaluator(value)
    is Text -> evaluator(value)
    is Color -> evaluator(value)
    is Image -> evaluator(value)
    is Font -> evaluator(value)
    is Drawing -> evaluator(value)
    is Void -> evaluator(value)

    is Value.Logged<*> -> TODO() //(evaluator<T, R>(value.value) as Evaluator<R>).logged(value.label)
    is Value.RunWhile<*> -> evaluator(value.value)
    is Value.Select<*> -> TODO()
    is Value.Sequence<*> -> TODO()
    is Value.StartWhen<*> -> evaluator(value.value)
    is Value.Stretch<*> -> evaluator(value.value)
  } as Evaluator<R>

fun Compiler.intEvaluator(value: Value<Integer>): IntEvaluator =
  animated<Integer, Int>(value).evaluator as IntEvaluator

fun Compiler.doubleEvaluator(value: Value<Number>): DoubleEvaluator =
  animated<Number, Double>(value).evaluator as DoubleEvaluator

fun <T: Value<T>, R> Compiler.objectEvaluator(value: Value<T>): ObjectEvaluator<R> =
  animated<T, R>(value).evaluator as ObjectEvaluator<R>

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
