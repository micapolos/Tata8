package micapolos.zexy

import micapolos.zexy.model.Animation as ModelAnimation

class Animation internal constructor(internal val model: ModelAnimation) {
  class Builder internal constructor(internal val animations: MutableList<Animation> = mutableListOf()) {
    fun instant(fn: Action2.Builder.() -> Unit) {
      animations.add(Animation(ModelAnimation.Instant(action(fn).model)))
    }

    fun pause(seconds: Double) {
      pause(seconds.value)
    }

    fun pause(seconds: Value<Number>) {
      animations.add(Animation(ModelAnimation.Pause(seconds.modelNumber)))
    }

    fun parallel(fn: Builder.() -> Unit) {
      animations.add(Animation(ModelAnimation.Parallel(Builder().apply { fn() }.animations.map { it.model })))
    }

    fun race(fn: Builder.() -> Unit) {
      animations.add(Animation(ModelAnimation.Race(Builder().apply { fn() }.animations.map { it.model })))
    }

    fun sequence(fn: Builder.() -> Unit) {
      animations.add(Animation(ModelAnimation.Sequence(Builder().apply { fn() }.animations.map { it.model })))
    }

    fun Value<Integer>.selectStep(fn: Builder.() -> Unit) {
      animations.add(
        Animation(
          ModelAnimation.SelectStep(
            modelInteger,
            Builder().apply { fn() }.animations.map { it.model })
        )
      )
    }

    fun Value<Integer>.selectStart(fn: Builder.() -> Unit) {
      animations.add(
        Animation(
          ModelAnimation.SelectStart(
            modelInteger,
            Builder().apply { fn() }.animations.map { it.model })
        )
      )
    }

    internal fun build(): Animation = Animation(ModelAnimation.Sequence(animations.map { it.model }))
  }
}


fun animation(fn: Animation.Builder.() -> Unit): Animation = Animation.Builder().apply { fn() }.build()