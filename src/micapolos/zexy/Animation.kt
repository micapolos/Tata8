package micapolos.zexy

import micapolos.zexy.examples.Zexy
import micapolos.zexy.model.Action as ModelAction
import micapolos.zexy.model.Animation as ModelAnimation

class Animation internal constructor(
  internal val model: ModelAnimation,
): Value<Animation>(model) {
  @Zexy
  class Block internal constructor() {
    internal val animationModels: MutableList<ModelAnimation> = mutableListOf()
    internal val actionBlock: Action.Block = Action.Block()

    internal fun build(): Animation = run {
      flushActions()
      val builtAnimationModels = buildAnimationModels()
      val builtAnimationModel = when {
        builtAnimationModels.isEmpty() -> ModelAnimation.Once(ModelAction.Empty)
        builtAnimationModels.singleOrNull() != null -> builtAnimationModels.single()
        else -> ModelAnimation.Parallel(builtAnimationModels)
      }
      Animation(builtAnimationModel)
    }

    internal fun buildAnimationModels() = run {
      flushActions()
      animationModels.toList().also { animationModels.clear() }
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

    fun everyStep(fn: Action.Block.() -> Unit) {
      add(ModelAnimation.EveryStep(actionModel(fn)))
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

    fun repeat(fn: Block.() -> Unit) {
      add(ModelAnimation.RepeatWhile(animation(fn).model, true.value.integer.modelInteger))
    }

    fun startOn(event: Value<Event>, fn: Block.() -> Unit) {
      add(ModelAnimation.StartOn(event.isOccurring.integer.modelInteger, animation { fn() }.model))
    }

    infix fun Value<Integer>.selectAction(fn: Action.Block.() -> Unit) {
      with(actionBlock) { select(fn) }
    }
  }
}

val infiniteAnimation = Animation(ModelAnimation.Infinite)

val Value<Action>.instant get() = Animation(ModelAnimation.Once(modelAction as ModelAction))

context(animationBlock: Animation.Block)
infix fun <T : Value<T>> Value<T>.bind(value: Value<T>) {
  with(animationBlock.actionBlock) { bind(value) }
}

context(animationBlock: Animation.Block)
infix fun <T : Value<T>> Value<T>.set(value: Value<T>) {
  with(animationBlock.actionBlock) { set(value) }
}

context(animationBlock: Animation.Block)
fun <T : Value<T>> Value<T>.showAnimated() {
  with(animationBlock.build()).show()
}

fun animation(fn: Animation.Block.() -> Unit): Animation =
  Animation.Block().apply(fn).build()

fun <T: Value<T>> animated(fn: Animation.Block.() -> T): Animated<T> =run {
  val block = Animation.Block()
  val value = block.fn()
  Animated(value, block.build())
}

fun sequence(fn: Animation.Block.() -> Unit): Animation =
  Animation.Block().apply(fn).build()

fun sequence(animation: Value<Animation>, vararg animations: Value<Animation>): Animation =
  Animation(ModelAnimation.Sequence(listOf(animation, *animations).map { it.model as ModelAnimation }))

fun parallel(animation: Value<Animation>, vararg animations: Value<Animation>): Animation =
  Animation(ModelAnimation.Parallel(listOf(animation, *animations).map { it.model as ModelAnimation }))

fun show(fn: Animation.Block.() -> Unit) {
  game.with(animation(fn)).show()
}
