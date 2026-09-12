package micapolos.zexy3

import micapolos.zexy3.model.Value as ModelValue
import micapolos.zexy3.model.Void as ModelVoid

open class Value<out T : Value<T>> internal constructor(internal val modelOrChildren: Any?)

internal val Value<*>.model get() = modelOrChildren as ModelValue<ModelVoid>
internal val Value<*>.children get() = modelOrChildren as List<Value<*>>
internal fun <T> Value<*>.children() = modelOrChildren as List<T>
internal val Value<*>.safeModel: ModelValue<*> get() =
  when (modelOrChildren) {
    is ModelValue<*> -> modelOrChildren
    else -> children[0].safeModel
  }

fun <T : Value<T>> sequence(vararg values: Value<T>): Value<T> =
  Value(ModelValue.Sequence(values.map { it.model }))

val <T : Value<T>> Value<T>.logged: Value<T> get() = Value(ModelValue.Logged(null, safeModel))

infix fun <T : Value<T>> Value<T>.loggedAs(label: String): Value<T> =
  Value(ModelValue.Logged(label, safeModel))

infix fun <T : Value<T>> Value<T>.then(value: Value<T>): Value<T> =
  sequence(this, value)

fun <T : Value<T>> Value<T>.finishAfter(activity: Value<Activity>): Value<T> =
  Value(ModelValue.Stateful(activity.model, model))

fun <T : Value<T>> Value<T>.raceWith(vararg values: Value<T>): Value<T> =
  Value(ModelValue.Race(model, values.map { it.model }))

fun <T : Value<T>> Value<T>.repeatWhile(b: Boolean): Value<T> = repeatWhile(b.value)

fun <T : Value<T>> Value<T>.repeatWhile(condition: Value<Bool>): Value<T> =
  Value(ModelValue.RepeatWhile(condition.bool.integer.modelInteger, model))

fun <T : Value<T>> Value<T>.repeat(): Value<T> = repeatWhile(true)

fun <T : Value<T>> Value<T>.also(fn: (Value<T>) -> Value<*>): Value<T> =
  Value(ModelValue.Stateful(fn(this).model, model))

fun <T : Value<T>> Value<T>.apply(fn: Value<T>.() -> Value<*>): Value<T> = also(fn)

fun <T : Value<T>> Value<T>.show() {
  noDrawing.also { logged }.show()
}

val Value<*>.everyFrame: Value<Activity>
  get() =
    Activity(ModelValue.EveryFrame(model))

val Value<*>.nextFrame: Value<Activity>
  get() =
    Activity(ModelValue.NextFrame(model))

fun <T: Value<T>> Value<T>.startOn(event: Value<Event>): Value<T> =
  Value(ModelValue.StartWhen(event.isOccurring.integer.modelInteger, model))