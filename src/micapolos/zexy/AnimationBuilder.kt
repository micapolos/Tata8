package micapolos.zexy

import micapolos.zexy.model.Animation as ModelAnimation

class Animation internal constructor(internal val model: ModelAnimation) {
  class Builder internal constructor(internal val animations: MutableList<Animation> = mutableListOf()) {
    fun instant(fn: Action2.Builder.() -> Unit): Animation =
      Animation(ModelAnimation.Instant(action(fn).model))

    fun parallel(fn: Builder.() -> Unit) =
      Animation(ModelAnimation.Parallel(Builder().apply { fn() }.animations.map { it.model }))

    fun race(fn: Builder.() -> Unit) =
      Animation(ModelAnimation.Race(Builder().apply { fn() }.animations.map { it.model }))

    fun sequence(fn: Builder.() -> Unit) =
      Animation(ModelAnimation.Sequence(Builder().apply { fn() }.animations.map { it.model }))

    fun selectStep(index: Value<Integer>, fn: Builder.() -> Unit) =
      Animation(ModelAnimation.SelectStep(index.modelInteger, Builder().apply { fn() }.animations.map { it.model }))

    fun selectStart(index: Value<Integer>, fn: Builder.() -> Unit) =
      Animation(ModelAnimation.SelectStart(index.modelInteger, Builder().apply { fn() }.animations.map { it.model }))
  }
}

fun animation(fn: Animation.Builder.() -> Unit): Animation = Animation.Builder().sequence(fn)