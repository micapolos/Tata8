package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.IndexType
import micapolos.zexy3.indexed.Integer
import micapolos.zexy3.indexed.Number
import micapolos.zexy3.indexed.Value
import micapolos.zexy3.indexed.Void
import micapolos.zexy3.runtime.*

fun Compiler.animation(indexed: Void): Animation =
  when (indexed) {
    Void.Empty -> noAnimation
    Void.Pause -> pauseAnimation { 1.0 }
    is Void.Set<*> -> noAnimation
    is Void.Parallel<*> -> parallel(indexed.values.map { animation(it) })
  }

fun <T: Value<T>, R> Compiler.evaluator(indexed: Void): Evaluator<R> =
  when (indexed) {
    Void.Empty -> ObjectEvaluator { Unit }
    Void.Pause -> ObjectEvaluator { Unit }
    is Void.Set<*> -> {
      val variable = indexed.variable
      val index = variable.index
      when (variable.indexType) {
        IndexType.INTEGER -> {
          val intEvaluator = intEvaluator(indexed.value as Integer)
          ObjectEvaluator { intArray[index] = intEvaluator.eval() }
        }
        IndexType.NUMBER -> {
          val doubleEvaluator = doubleEvaluator(indexed.value as Number)
          ObjectEvaluator { doubleArray[index] = doubleEvaluator.eval() }
        }
        IndexType.OTHER -> {
          val objectEvaluator = objectEvaluator<T, R>(indexed.value as T)
          ObjectEvaluator { objectArray[index] = objectEvaluator.eval() }
        }
      }
    }
    is Void.Parallel<*> -> ObjectEvaluator { null }
  } as Evaluator<R>