package micapolos.zexy3

import micapolos.zexy3.model.Value as ModelValue
import micapolos.zexy3.model.Void as ModelVoid

open class Value<T : Value<T>> internal constructor(internal val modelOrChildren: Any?)

internal val Value<*>.model get() = modelOrChildren as ModelValue<ModelVoid>
internal val Value<*>.children get() = modelOrChildren as List<Value<*>>
internal fun <T> Value<*>.children() = modelOrChildren as List<T>

fun <T: Value<T>> parallel(vararg values: Value<T>): Value<Void> =
  Void(ModelVoid.Parallel(values.map { it.model }))

fun <T: Value<T>> sequence(vararg values: Value<T>): Value<T> =
  Value(ModelValue.Sequence(values.map { it.model }))

val <T: Value<T>> Value<T>.logged: Value<T> get() = Value(ModelValue.Logged(null, model))
fun <T: Value<T>> Value<T>.logged(label: String): Value<T> = Value(ModelValue.Logged(label, model))