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
        parallel(animatedSeconds.animation, pauseAnimation { secondsEvaluator.eval() })
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
        parallel(
          animatedValue.animation,
          when (valueEvaluator) {
            is IntEvaluator ->
              actionAnimation {
                state.intArray[typedIndex] = valueEvaluator.eval()
                state.animatedArray[index] = null
              }

            is DoubleEvaluator ->
              actionAnimation {
                state.doubleArray[typedIndex] = valueEvaluator.eval()
                state.animatedArray[index] = null
              }

            is ObjectEvaluator<*> ->
              actionAnimation {
                state.objectArray[typedIndex] = valueEvaluator.eval()
                state.animatedArray[index] = null
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
        ObjectEvaluator { Unit },
        parallel(
          animatedValue.animation,
          when (variable.indexType) {
            IndexType.INTEGER ->
              actionAnimation {
                state.animatedArray[index] = animatedValue
              }

            IndexType.NUMBER -> {
              actionAnimation {
                state.animatedArray[index] = animatedValue
              }
            }

            IndexType.OBJECT -> {
              actionAnimation {
                state.objectArray[typedIndex] = null  // avoids retention
                state.animatedArray[index] = animatedValue
              }
            }
          }))
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
