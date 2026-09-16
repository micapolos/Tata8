package micapolos.zexy.compiler

import micapolos.zexy.indexed.Animation
import micapolos.zexy.runtime.*
import micapolos.zexy.runtime.Animation as RuntimeAnimation

fun Compiler.evaluator(animation: Animation): Evaluator<RuntimeAnimation> =
  runtime(animation).let { ObjectEvaluator { it } }

fun Compiler.runtime(animation: Animation): RuntimeAnimation =
  when (animation) {
    Animation.Empty -> infiniteAnimation
    is Animation.Once -> instantAnimation(objectEvaluator(animation.action))
    is Animation.EveryStep -> everyStepAnimation(objectEvaluator(animation.action))
    is Animation.Pause -> pauseAnimation(doubleEvaluator(animation.seconds))
    is Animation.Parallel -> parallel(animation.animations.map { objectEvaluator(it) })
    is Animation.Sequence -> SequenceAnimation(animation.animations.map { objectEvaluator(it) })
    is Animation.Race -> race(animation.animations.map { objectEvaluator(it) })
    is Animation.RepeatWhile -> objectEvaluator<RuntimeAnimation>(animation.animation).repeatWhile(intEvaluator(animation.condition))
    is Animation.StartOn -> startOnAnimation(intEvaluator(animation.trigger), objectEvaluator(animation.animation))
  }