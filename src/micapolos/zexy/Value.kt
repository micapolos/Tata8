package micapolos.zexy

import micapolos.zexy.model.Action as ModelAction
import micapolos.zexy.model.Value as ModelValue

sealed class Impl {
  class WithModel(val model: ModelValue<*>): Impl()
  class WithChildren(val children: List<Value<*>>): Impl()
}

interface Value<out T : Value<T>> {
  val impl: Impl
}

open class ValueWithModel<out T: Value<T>>(internal val model: ModelValue<*>): Value<T> {
  override val impl: Impl get() = Impl.WithModel(model)
}

open class ValueWithChildren<out T: Value<T>>(vararg val children: Value<*>): Value<T> {
  override val impl: Impl get() = Impl.WithChildren(children.toList())
}

internal val Value<*>.model get() = (impl as Impl.WithModel).model as ModelValue<ModelAction>
internal val Value<*>.children get() = (impl as Impl.WithChildren).children
internal fun <T> Value<*>.children() = children as List<T>
internal val Value<*>.safeModel: ModelValue<*> get() =
  when (val impl = this.impl) {
    is Impl.WithModel -> impl.model
    is Impl.WithChildren -> impl.children[0].model
  }

val <T : Value<T>> Value<T>.logged: Value<T> get() = loggedAs(null)

infix fun <T : Value<T>> Value<T>.loggedAs(label: String?): Value<T> =
  ValueWithModel(ModelValue.Logged(label, safeModel))

fun <T: Value<T>> Value<T>.animated(fn: Animation.Block.(Value<T>) -> Unit): Animated<T> =
  with(animation { fn(this@animated) })

fun <T: Value<T>> Value<T>.with(animation: Value<Animation>): Animated<T> =
  Animated(this, animation)

fun <T: Value<T>> Value<T>.withAnimation(fn: Animation.Block.(Value<T>) -> Unit): Animated<T> =
  with(animation { fn(this@withAnimation) })

fun <T: Value<T>> Value<T>.withSequence(fn: Animation.Block.(Value<T>) -> Unit): Animated<T> =
  with(animation { sequence { fn(this@withSequence) } })

fun <T : Value<T>> Value<T>.show() {
  noDrawing.also { logged }.show()
}
