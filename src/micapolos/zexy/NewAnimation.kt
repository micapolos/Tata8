package micapolos.zexy

import micapolos.zexy.model.Animation as ModelAnimation

class Animation internal constructor(internal val model: ModelAnimation) {
  class Builder internal constructor() {
    internal val animationModels: MutableList<ModelAnimation> = mutableListOf()
    internal val actionBuilder: Action2.Builder = Action2.Builder()

    internal fun build(): Animation = run {
      flushActions()
      val animation = when {
        animationModels.isEmpty() -> ModelAnimation.Empty
        animationModels.singleOrNull() != null -> animationModels.single()
        else -> ModelAnimation.Sequence(animationModels)
      }
      animationModels.clear()
      Animation(animation)
    }

    internal fun buildAnimationModels() = run {
      flushActions()
      animationModels
    }

    internal fun flushActions() {
      actionBuilder.buildModelOrNull()?.let { modelAction ->
        animationModels.add(ModelAnimation.Instant(modelAction))
      }
    }

    internal fun add(animationModel: ModelAnimation) {
      flushActions()
      animationModels.add(animationModel)
    }

    fun pause(seconds: Int) {
      pause(seconds.toDouble())
    }

    fun pause(seconds: Double) {
      pause(seconds.value)
    }

    fun pause(seconds: Value<Number>) {
      add(ModelAnimation.Pause(seconds.modelNumber))
    }

    fun instant(fn: Builder.() -> Unit) {
      actionBuilder.buildModelOrNull()?.let { modelAction ->
        add(ModelAnimation.Instant(modelAction))
      }
    }

    fun parallel(fn: Builder.() -> Unit) {
      add(ModelAnimation.Parallel(Builder().apply { fn() }.buildAnimationModels()))
    }

    fun race(fn: Builder.() -> Unit) {
      add(ModelAnimation.Race(Builder().apply { fn() }.buildAnimationModels()))
    }

    fun sequence(fn: Builder.() -> Unit) {
      add(ModelAnimation.Sequence(Builder().apply { fn() }.buildAnimationModels()))
    }

    infix fun Value<Integer>.selectStep(fn: Builder.() -> Unit) {
      add(ModelAnimation.SelectStep(modelInteger, Builder().apply { fn() }.buildAnimationModels()))
    }

    infix fun Value<Integer>.selectStart(fn: Builder.() -> Unit) {
      add(ModelAnimation.SelectStart(modelInteger, Builder().apply { fn() }.buildAnimationModels()))
    }

    infix fun <T : Value<T>> Value<T>.set(value: Value<T>) {
      with(actionBuilder) { set(value) }
    }

    infix fun <T : Value<T>> Value<T>.capture(value: Value<T>) {
      with(actionBuilder) { capture(value) }
    }

    infix fun Value<Integer>.selectAction(fn: Action2.Builder.() -> Unit) {
      with(actionBuilder) { select(fn) }
    }
  }
}

fun animation(fn: Animation.Builder.() -> Unit): Animation = Animation.Builder().apply { fn() }.build()

fun Animation.show() {
  game.with(this).show()
}