package micapolos.zexy.compiler

import micapolos.zexy.indexed.Animation
import micapolos.zexy.runtime.*
import micapolos.zexy.runtime.Animation as RuntimeAnimation

fun Compiler.evaluator(animation: Animation): Evaluator<RuntimeAnimation> =
  runtime(animation).let { ObjectEvaluator { it } }

fun Compiler.runtime(animation: Animation): RuntimeAnimation =
  when (animation) {
    Animation.Empty -> infiniteAnimation
    is Animation.Once -> instantAnimation(runtime(animation.action))
    is Animation.EveryStep -> everyStepAnimation(runtime(animation.action))
    is Animation.Pause -> pauseAnimation(doubleEvaluator(animation.seconds))
    is Animation.Parallel -> parallel(animation.animations.map { evaluator(it) })
    is Animation.Sequence -> SequenceAnimation(animation.animations.map { runtime(it) })
    is Animation.Race -> race(animation.animations.map { runtime(it) })
    is Animation.RepeatWhile -> runtime(animation.animation).repeatWhile(intEvaluator(animation.condition))
    is Animation.SelectStart -> selectStartAnimation(
      intEvaluator(animation.trigger),
      intEvaluator(animation.index),
      animation.animations.map { evaluator(it) })
    is Animation.SelectStep -> selectStepAnimation(
      intEvaluator(animation.index),
      animation.animations.map { evaluator(it) })
  }