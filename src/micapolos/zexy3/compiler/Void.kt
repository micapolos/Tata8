package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.*
import micapolos.zexy3.indexed.Number
import micapolos.zexy3.runtime.*

fun Compiler.animation(indexed: Void): Animation =
  when (indexed) {
    Void.Empty -> noAnimation
    Void.Pause -> DoubleEvaluator { 1.0 }.pause
    is Void.Set<*> -> noAnimation
    is Void.Parallel<*> -> parallel(indexed.values.map { animation(it) })
  }

fun <T : Value<T>> Compiler.voidEvaluator(indexed: Void): Evaluator<*> =
  when (indexed) {
    Void.Empty -> ObjectEvaluator { Unit }
    Void.Pause -> ObjectEvaluator { Unit }
    is Void.Set<*> -> {
      val variable = indexed.variable
      val index = variable.typedIndex
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
          val objectEvaluator = objectEvaluator(indexed.value as T)
          ObjectEvaluator { objectArray[index] = objectEvaluator.eval() }
        }
      }
    }

    is Void.Parallel<*> -> parallelEvaluator(indexed.values.map { evaluator(it) })
  }