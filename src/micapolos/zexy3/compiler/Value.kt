package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.*
import micapolos.zexy3.indexed.Drawing
import micapolos.zexy3.indexed.Number
import micapolos.zexy3.runtime.*

fun <T: Value<T>, A> Compiler.animated(value: Value<T>): Animated<A> =
  Animated(evaluator(value), animation(value))

fun <T: Value<T>, R> Compiler.evaluator(value: Value<T>): Evaluator<R> =
  when (value) {
    is Integer -> evaluator(value)
    is Number -> evaluator(value)
    is Variable -> evaluator(value)
    else -> TODO()
  } as Evaluator<R>

fun Compiler.intEvaluator(value: Value<Integer>): IntEvaluator =
  animated<Integer, Int>(value).evaluator as IntEvaluator

fun Compiler.doubleEvaluator(value: Value<Number>): DoubleEvaluator =
  animated<Number, Double>(value).evaluator as DoubleEvaluator

fun <T: Value<T>, R> Compiler.objectEvaluator(value: Value<T>): ObjectEvaluator<R> =
  animated<T, R>(value).evaluator as ObjectEvaluator<R>

fun <T: Value<T>> Compiler.animation(value: Value<T>): Animation =
  when (value) {
    is Integer -> animation(value)
    is Number -> animation(value)
    is Text -> animation(value)
    is Image -> animation(value)
    is Font -> animation(value)
    is Drawing -> animation(value)
    is Variable -> animation(value)
    is Void -> animation(value)
    is Color -> animation(value)

    is Value.Logged -> noAnimation
    else -> TODO()
  }
