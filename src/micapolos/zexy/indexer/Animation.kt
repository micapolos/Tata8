package micapolos.zexy.indexer

import micapolos.zexy.indexed.Animation
import micapolos.zexy.model.Animation as ModelAnimation

fun Indexer.indexedAnimation(model: ModelAnimation): Animation =
  when (model) {
    ModelAnimation.Infinite -> Animation.Empty
    is ModelAnimation.Once -> Animation.Once(indexedAction(model.action))
    is ModelAnimation.EveryStep -> Animation.EveryStep(indexedAction(model.action))
    is ModelAnimation.Pause -> Animation.Pause(indexed(model.seconds))
    is ModelAnimation.Parallel -> Animation.Parallel(model.animations.map { indexedAnimation(it) })
    is ModelAnimation.Sequence -> Animation.Sequence(model.animations.map { indexedAnimation(it) })
    is ModelAnimation.Race -> Animation.Race(model.animations.map { indexedAnimation(it) })
    is ModelAnimation.RepeatWhile -> Animation.RepeatWhile(indexedAnimation(model.animation), indexed(model.condition))
    is ModelAnimation.SelectStart -> Animation.SelectStart(indexed(model.trigger), indexed(model.index), model.animations.map { indexedAnimation(it) })
    is ModelAnimation.SelectStep -> Animation.SelectStep(indexed(model.index), model.animations.map { indexedAnimation(it) })
  }