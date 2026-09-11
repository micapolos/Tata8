package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.IndexType
import micapolos.zexy3.indexed.Value
import micapolos.zexy3.indexed.Void
import micapolos.zexy3.runtime.*

fun Compiler.animatedVoid(indexed: Void): Animated<*> =
  when (indexed) {
    is Void.Pause -> Animated(voidEvaluator(indexed), voidAnimation(indexed))
    is Void.Capture<*> -> {
      val animatedValue = animated(indexed.value)
      val valueEvaluator = animatedValue.evaluator
      val variable = indexed.variable
      val typedIndex = variable.typedIndex
      val index = variable.index
      Animated(
        ObjectEvaluator { Unit },
        parallel(
          animatedValue.animation,
          when (valueEvaluator) {
            is IntEvaluator -> actionAnimation {
              intArray[typedIndex] = valueEvaluator.eval()
              animatedValues[index] = null
            }

            is DoubleEvaluator -> actionAnimation {
              doubleArray[typedIndex] = valueEvaluator.eval()
              animatedValues[index] = null
            }

            is ObjectEvaluator<*> -> actionAnimation {
              objectArray[typedIndex] = valueEvaluator.eval()
              animatedValues[index] = null
            }
          }
        )
      )
    }

    is Void.Set<*> -> {
      val animatedValue = animated(indexed.value)
      val variable = indexed.variable
      val typedIndex = variable.typedIndex
      val index = variable.index
      Animated(
        when (variable.indexType) {
          IndexType.INTEGER -> {
            ObjectEvaluator {
              animatedValues[index] = animatedValue
            }
          }

          IndexType.NUMBER -> {
            ObjectEvaluator {
              animatedValues[index] = animatedValue
            }
          }

          IndexType.OBJECT -> {
            ObjectEvaluator {
              objectArray[typedIndex] = null  // avoids retention
              animatedValues[index] = animatedValue
            }
          }
        },
        instantAnimation)
    }

    is Void.Parallel -> Animated(voidEvaluator(indexed), voidAnimation(indexed))
    is Void.Race -> Animated(voidEvaluator(indexed), voidAnimation(indexed))
  }

fun Compiler.voidAnimation(indexed: Void): Animation =
  when (indexed) {
    is Void.Pause -> doubleEvaluator(indexed.seconds).pause
    is Void.Set<*> -> animatedVoid(indexed).animation
    is Void.Capture<*> -> TODO()
    is Void.Parallel -> parallel(indexed.values.map { animation(it) })
    is Void.Race -> race(indexed.values.map { animation(it) })
  }

fun <T : Value<T>> Compiler.voidEvaluator(indexed: Void): Evaluator<*> =
  when (indexed) {
    is Void.Pause -> ObjectEvaluator { Unit }
    is Void.Set<*> -> animatedVoid(indexed).evaluator
    is Void.Capture<*> -> TODO()
    is Void.Parallel -> parallelEvaluator(indexed.values.map { evaluator(it) })
    is Void.Race -> parallelEvaluator(indexed.values.map { evaluator(it) })
  }