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

    is Void.Set<*> -> {
      val animated = animated(indexed.value)
      val evaluator = animated.evaluator
      val variable = indexed.variable
      val typedIndex = variable.typedIndex
      val index = variable.index
      Animated(
        ObjectEvaluator { Unit },
        parallel(
          animated.animation,
          when (evaluator) {
            is IntEvaluator ->
              actionAnimation {
                state.setInt(typedIndex, index, evaluator.eval())
              }

            is DoubleEvaluator ->
              actionAnimation {
                state.setDouble(typedIndex, index, evaluator.eval())
              }

            is ObjectEvaluator<*> ->
              actionAnimation {
                state.setObject(typedIndex, index, evaluator.eval())
              }
          }
        )
      )
    }

    is Void.Bind<*> -> {
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
                state.bindInt(index, animatedValue)
              }

            IndexType.NUMBER -> {
              actionAnimation {
                state.bindDouble(index, animatedValue)
              }
            }

            IndexType.OBJECT -> {
              actionAnimation {
                state.bindObject(typedIndex, index, animatedValue)
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
