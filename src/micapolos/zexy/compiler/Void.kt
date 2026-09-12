package micapolos.zexy.compiler

import micapolos.zexy.indexed.IndexType
import micapolos.zexy.indexed.Void
import micapolos.zexy.runtime.*

fun Compiler.animatedVoid(indexed: Void): Animated<*> =
  when (indexed) {
    is Void.Empty ->
      Animated(ObjectEvaluator { Unit }, instantAnimation)

    is Void.Pause -> {
      val animatedSeconds = animated(indexed.seconds)
      val secondsEvaluator = animatedSeconds.evaluator as DoubleEvaluator
      Animated(
        ObjectEvaluator { Unit },
        pauseAnimation { secondsEvaluator.eval() }
      )
    }

    is Void.Capture<*> -> {
      val animatedValue = animated(indexed.value)
      val valueEvaluator = animatedValue.evaluator
      val variable = indexed.variable
      val typedIndex = variable.typedIndex
      val index = variable.index
      Animated(
        ObjectEvaluator { Unit },
        when (valueEvaluator) {
          is IntEvaluator ->
            actionAnimation {
              intArray[typedIndex] = valueEvaluator.eval()
              animatedValues[index] = null
            }

          is DoubleEvaluator ->
            actionAnimation {
              doubleArray[typedIndex] = valueEvaluator.eval()
              animatedValues[index] = null
            }

          is ObjectEvaluator<*> ->
            actionAnimation {
              objectArray[typedIndex] = valueEvaluator.eval()
              animatedValues[index] = null
            }
        }
      )
    }

    is Void.Set<*> -> {
      val animatedValue = animated(indexed.value)
      val variable = indexed.variable
      val typedIndex = variable.typedIndex
      val index = variable.index
      Animated(
        ObjectEvaluator { Unit },
        when (variable.indexType) {
          IndexType.INTEGER ->
            actionAnimation {
              animatedValues[index] = animatedValue
            }

          IndexType.NUMBER -> {
            actionAnimation {
              animatedValues[index] = animatedValue
            }
          }

          IndexType.OBJECT -> {
            actionAnimation {
              objectArray[typedIndex] = null  // avoids retention
              animatedValues[index] = animatedValue
            }
          }
        })
    }

    is Void.Parallel -> {
      val animatedValues = indexed.values.map { animated(it) }
      Animated(
        parallelEvaluator(animatedValues.map { it.evaluator }),
        parallel(animatedValues.map { it.animation })
      )
    }

    is Void.Race -> {
      val animatedValues = indexed.values.map { animated(it) }
      // TODO: Update model to split first from others.
      val firstEvaluator = animatedValues.first().evaluator
      Animated(
        firstEvaluator,
        race(animatedValues.map { it.animation })
      )
    }
  }
