package micapolos.zexy.compiler

import micapolos.zexy.indexed.Animation
import micapolos.zexy.runtime.*
import micapolos.zexy.runtime.Animation as RuntimeAnimation

fun Compiler.compile(animation: Animation): RuntimeAnimation =
  when (animation) {
    Animation.Empty -> infiniteAnimation
    is Animation.Instant -> instantAnimation(compile(animation.action))
    is Animation.Pause -> pauseAnimation(doubleEvaluator(animation.seconds))
    is Animation.Parallel -> parallel(animation.animations.map { compile(it) })
    is Animation.Sequence -> SequenceAnimation(animation.animations.map { compile(it) })
    is Animation.Race -> race(animation.animations.map { compile(it) })
    is Animation.RepeatWhile -> compile(animation.animation).repeatWhile(intEvaluator(animation.condition))
    is Animation.SelectStart -> selectStartAnimation(intEvaluator(animation.index), animation.animations.map { compile(it) })
    is Animation.SelectStep -> selectStepAnimation(intEvaluator(animation.index), animation.animations.map { compile(it) })
  }