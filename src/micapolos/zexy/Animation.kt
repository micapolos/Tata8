package micapolos.zexy

import micapolos.zexy.Action.Block
import micapolos.zexy.model.Action as ModelAction
import micapolos.zexy.model.Animation as ModelAnimation
import micapolos.zexy.model.Value as ModelValue

class Animation internal constructor(model: ModelValue<ModelAnimation>): ValueWithModel<Animation>(model) {
  @Zexy
  class Block internal constructor() {
    internal val animationModels: MutableList<ModelValue<ModelAnimation>> = mutableListOf()
    internal val actionBlock: Action.Block = Action.Block()

    internal fun build(): Value<Animation> = run {
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

    internal fun add(animationModel: ModelValue<ModelAnimation>) {
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
      repeatWhile(true, fn)
    }

    fun repeatWhile(condition: Boolean, fn: Block.() -> Unit) {
      repeatWhile(condition.value, fn)
    }

    fun repeatWhile(condition: Value<Bool>, fn: Block.() -> Unit) {
      add(ModelAnimation.RepeatWhile(animation(fn).modelAnimation, condition.integer.modelInteger))
    }

    fun on(event: Value<Event>, fn: Block.() -> Unit) {
      add(ModelAnimation.On(event.isOccurring.integer.modelInteger, animation { fn() }.modelAnimation))
    }

    infix fun Value<Integer>.selectFrom(fn: Block.() -> Unit) {
      add(
        ModelValue.Select(
          modelInteger,
          Block().apply { fn() }.buildAnimationModels()))
    }

    fun whenTrue(condition: Value<Bool>, fn: Block.() -> Unit) {
      condition.integer selectFrom {
        doNothing
        animation(fn)
      }
    }
  }
}

internal val Value<Animation>.modelAnimation get() = model as ModelValue<ModelAnimation>

val infiniteAnimation = Animation(ModelAnimation.Infinite)

val Value<Action>.instant get() = Animation(ModelAnimation.Once(modelAction))

val Value<Action>.everyStep get() = Animation(ModelAnimation.EveryStep(modelAction))

fun pause(seconds: Double) = pause(seconds.value)

fun pause(seconds: Value<Number>) = Animation(ModelAnimation.Pause(seconds.modelNumber))

fun parallel(animation: Value<Animation>, vararg animations: Value<Animation>): Animation =
  parallel(listOf(animation, *animations))

fun parallel(animations: List<Value<Animation>>): Animation =
  Animation(ModelAnimation.Parallel(animations.map { it.modelAnimation }))

fun sequence(animation: Value<Animation>, vararg animations: Value<Animation>): Animation =
  sequence(listOf(animation, *animations))

fun sequence(animations: List<Value<Animation>>): Animation =
  Animation(ModelAnimation.Sequence(animations.map { it.modelAnimation }))

fun Value<Animation>.repeatWhile(condition: Boolean): Animation =
  repeatWhile(condition.value)

fun Value<Animation>.repeatWhile(condition: Value<Bool>): Animation =
  Animation(ModelAnimation.RepeatWhile(modelAnimation, condition.modelInteger))

val Value<Animation>.repeat get(): Animation =
  repeatWhile(true)

fun Value<Animation>.on(event: Value<Event>): Animation =
  Animation(ModelAnimation.On(event.modelInteger, modelAnimation))


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

fun animation(fn: Animation.Block.() -> Unit): Value<Animation> =
  Animation.Block().apply(fn).build()

fun sequence(fn: Animation.Block.() -> Unit): Value<Animation> =
  Animation.Block().apply(fn).build()
