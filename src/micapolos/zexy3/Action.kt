package micapolos.zexy3

import micapolos.zexy3.model.Value as ModelValue
import micapolos.zexy3.model.Void as ModelVoid

class Action internal constructor(model: Any) : Value<Action>(model)

fun <T : Value<T>> Variable<T>.set(value: Value<T>): Value<Action> =
  Action(ModelVoid.Set(erasedModelVariable, value.model))

val Value<Action>.activity: Value<Activity> get() = Activity(model)

val Value<Action>.everyFrame: Value<Activity> get() =
  Activity(ModelValue.Frame(model))

fun sequence(actions: List<Value<Action>>): Value<Action> =
  Action(ModelValue.Sequence(actions.map { it.model }))

fun sequence(action: Value<Action>, vararg actions: Value<Action>): Value<Action> =
  sequence(listOf(action, *actions))
