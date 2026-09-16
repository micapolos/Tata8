package micapolos.zexy.indexer

import micapolos.zexy.indexed.Animation
import micapolos.zexy.model.Animation as ModelAnimation

fun Indexer.indexedAnimation(model: ModelAnimation): Animation =
  when (model) {
    ModelAnimation.Infinite -> Animation.Empty
    is ModelAnimation.Once -> Animation.Once(indexed(model.action))
    is ModelAnimation.EveryStep -> Animation.EveryStep(indexed(model.action))
    is ModelAnimation.Pause -> Animation.Pause(indexed(model.seconds))
    is ModelAnimation.Parallel -> Animation.Parallel(model.animations.map { indexed(it) })
    is ModelAnimation.Sequence -> Animation.Sequence(model.animations.map { indexed(it) })
    is ModelAnimation.Race -> Animation.Race(model.animations.map { indexed(it) })
    is ModelAnimation.RepeatWhile -> Animation.RepeatWhile(indexed(model.animation), indexed(model.condition))
    is ModelAnimation.On -> Animation.On(indexed(model.trigger), indexed(model.animation))
  }