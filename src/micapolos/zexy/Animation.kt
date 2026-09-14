package micapolos.zexy

import micapolos.zexy.examples.Zexy
import micapolos.zexy.model.Action as ModelAction
import micapolos.zexy.model.Animation as ModelAnimation

class Animation internal constructor(
  internal val model: ModelAnimation,
  internal val parentOrNull: Animation? = null,
  internal var hasChild: Boolean = false,
) {
  init {
    if (parentOrNull != null) {
      if (parentOrNull.hasChild) {
        error("Animation already has a child")
      } else {
        parentOrNull.hasChild = true
      }
    }
  }
  @Zexy
  class Builder internal constructor() {
    internal val animationModels: MutableList<ModelAnimation> = mutableListOf()
    internal val actionBuilder: Action2.Builder = Action2.Builder()

    internal fun build(): Animation = run {
      flushActions()
      val animationModels = buildAnimationModels()
      val animationModel = when {
        animationModels.isEmpty() -> ModelAnimation.Once(ModelAction.Empty)
        animationModels.singleOrNull() != null -> animationModels.single()
        else -> ModelAnimation.Sequence(animationModels.toList())
      }
      this.animationModels.clear()
      Animation(animationModel)
    }

    internal fun buildAnimationModels() = run {
      flushActions()
      animationModels.toList()
    }

    internal fun flushActions() {
      actionBuilder.buildModelOrNull()?.let { modelAction ->
        animationModels.add(ModelAnimation.Once(modelAction))
      }
    }

    internal fun add(animationModel: ModelAnimation) {
      flushActions()
      animationModels.add(animationModel)
    }

    val doNothing get() = sequence {  }

    infix fun pause(seconds: Int) {
      pause(seconds.toDouble())
    }

    infix fun pause(seconds: Double) {
      pause(seconds.value)
    }

    infix fun pause(seconds: Value<Number>) {
      add(ModelAnimation.Pause(seconds.modelNumber))
    }

    fun once(fn: Action2.Builder.() -> Unit) {
      add(ModelAnimation.Once(actionModel(fn)))
    }

    fun everyFrame(fn: Action2.Builder.() -> Unit) {
      add(ModelAnimation.EveryFrame(actionModel(fn)))
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

    fun startOn(event: Value<Event>, fn: Builder.() -> Unit) {
      event.isOccurring.integer.selectStart {
        doNothing
        sequence(fn)
      }
    }

    infix fun Value<Integer>.selectAction(fn: Action2.Builder.() -> Unit) {
      with(actionBuilder) { select(fn) }
    }
  }
}

context(animationBuilder: Animation.Builder)
infix fun <T : Value<T>> Value<T>.bind2(value: Value<T>) {
  with(animationBuilder.actionBuilder) { bind2(value) }
}

context(animationBuilder: Animation.Builder)
infix fun <T : Value<T>> Value<T>.set2(value: Value<T>) {
  with(animationBuilder.actionBuilder) { set2(value) }
}

context(animationBuilder: Animation.Builder)
fun <T : Value<T>> Value<T>.show2() {
  with(animationBuilder.build()).show()
}

fun animation(fn: Animation.Builder.() -> Unit): Animation =
  Animation.Builder().apply { fn() }.build()

fun Animation.show() {
  game.with(this).show()
}