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
  class Block internal constructor() {
    internal val animationModels: MutableList<ModelAnimation> = mutableListOf()
    internal val actionBlock: Action.Block = Action.Block()

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
      actionBlock.buildModelOrNull()?.let { modelAction ->
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

    fun once(fn: Action.Block.() -> Unit) {
      add(ModelAnimation.Once(actionModel(fn)))
    }

    fun everyFrame(fn: Action.Block.() -> Unit) {
      add(ModelAnimation.EveryFrame(actionModel(fn)))
    }

    fun parallel(fn: Block.() -> Unit) {
      add(ModelAnimation.Parallel(Block().apply { fn() }.buildAnimationModels()))
    }

    fun race(fn: Block.() -> Unit) {
      add(ModelAnimation.Race(Block().apply { fn() }.buildAnimationModels()))
    }

    fun sequence(fn: Block.() -> Unit) {
      add(ModelAnimation.Sequence(Block().apply { fn() }.buildAnimationModels()))
    }

    infix fun Value<Integer>.selectStep(fn: Block.() -> Unit) {
      add(ModelAnimation.SelectStep(modelInteger, Block().apply { fn() }.buildAnimationModels()))
    }

    infix fun Value<Integer>.selectStart(fn: Block.() -> Unit) {
      add(ModelAnimation.SelectStart(modelInteger, Block().apply { fn() }.buildAnimationModels()))
    }

    fun startOn(event: Value<Event>, fn: Block.() -> Unit) {
      event.isOccurring.integer.selectStart {
        doNothing
        sequence(fn)
      }
    }

    infix fun Value<Integer>.selectAction(fn: Action.Block.() -> Unit) {
      with(actionBlock) { select(fn) }
    }
  }
}

context(animationBlock: Animation.Block)
infix fun <T : Value<T>> Value<T>.bind2(value: Value<T>) {
  with(animationBlock.actionBlock) { bind2(value) }
}

context(animationBlock: Animation.Block)
infix fun <T : Value<T>> Value<T>.set2(value: Value<T>) {
  with(animationBlock.actionBlock) { set2(value) }
}

context(animationBlock: Animation.Block)
fun <T : Value<T>> Value<T>.show2() {
  with(animationBlock.build()).show()
}

fun animation(fn: Animation.Block.() -> Unit): Animation =
  Animation.Block().apply { fn() }.build()

fun Animation.show() {
  game.with(this).show()
}