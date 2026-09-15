package micapolos.zexy

import micapolos.zexy.model.Value as ModelValue
import micapolos.zexy.model.Void as ModelVoid

open class Value<out T : Value<T>> internal constructor(internal val modelOrChildren: Any?)

internal val Value<*>.model get() = modelOrChildren as ModelValue<ModelVoid>
internal val Value<*>.children get() = modelOrChildren as List<Value<*>>
internal fun <T> Value<*>.children() = modelOrChildren as List<T>
internal val Value<*>.safeModel: ModelValue<*> get() =
  when (modelOrChildren) {
    is ModelValue<*> -> modelOrChildren
    else -> children[0].safeModel
  }

val <T : Value<T>> Value<T>.logged: Value<T> get() = loggedAs(null)

infix fun <T : Value<T>> Value<T>.loggedAs(label: String?): Value<T> =
  Value(ModelValue.Logged(label, safeModel))

fun <T: Value<T>> Value<T>.animated(fn: Animation.Block.() -> Unit): Animated<T> =
  with(animation(fn))

fun <T: Value<T>> Value<T>.with(animation: Animation): Animated<T> =
  Animated(this, animation)

fun <T: Value<T>> Value<T>.withAnimation(fn: Animation.Block.(Value<T>) -> Unit): Animated<T> =
  with(animation { fn(this@withAnimation) })

fun <T: Value<T>> Value<T>.withSequence(fn: Animation.Block.(Value<T>) -> Unit): Animated<T> =
  with(animation { sequence { fn(this@withSequence) } })

fun <T : Value<T>> Value<T>.show() {
  noDrawing.also { logged }.show()
}
