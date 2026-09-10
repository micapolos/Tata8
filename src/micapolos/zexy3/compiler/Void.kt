package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.*
import micapolos.zexy3.indexed.Number
import micapolos.zexy3.runtime.*

fun Compiler.animatedVoid(indexed: Void): Animated<*> =
  when (indexed) {
    is Void.Pause -> Animated(voidEvaluator(indexed), voidAnimation(indexed))
    is Void.Capture<*> -> Animated(voidEvaluator(indexed), voidAnimation(indexed))
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
    is Void.Capture<*> -> instantAnimation
    is Void.Parallel -> parallel(indexed.values.map { animation(it) })
    is Void.Race -> race(indexed.values.map { animation(it) })
  }

fun <T : Value<T>> Compiler.voidEvaluator(indexed: Void): Evaluator<*> =
  when (indexed) {
    is Void.Pause -> ObjectEvaluator { Unit }
    is Void.Set<*> -> animatedVoid(indexed).evaluator
    is Void.Capture<*> -> {
      val variable = indexed.variable
      val typedIndex = variable.typedIndex
      val index = variable.index
      when (variable.indexType) {
        IndexType.INTEGER -> {
          val intEvaluator = intEvaluator(indexed.value as Integer)
          ObjectEvaluator {
            animatedValues[index] = null
            intArray[typedIndex] = intEvaluator.eval()
          }
        }

        IndexType.NUMBER -> {
          val doubleEvaluator = doubleEvaluator(indexed.value as Number)
          ObjectEvaluator {
            animatedValues[index] = null
            doubleArray[typedIndex] = doubleEvaluator.eval()
          }
        }

        IndexType.OBJECT -> {
          val objectEvaluator = objectEvaluator(indexed.value as T)
          ObjectEvaluator {
            animatedValues[index] = null
            objectArray[typedIndex] = objectEvaluator.eval()
          }
        }
      }
    }
    is Void.Parallel -> parallelEvaluator(indexed.values.map { evaluator(it) })
    is Void.Race -> parallelEvaluator(indexed.values.map { evaluator(it) })
  }