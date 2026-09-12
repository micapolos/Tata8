package micapolos.zexy

import micapolos.zexy.model.Value as ModelValue
import micapolos.zexy.model.Variable as ModelVariable
import micapolos.zexy.model.Void as ModelVoid

class Action internal constructor(model: Any) : Value<Action>(model)

val noAction = Action(ModelVoid.Empty)

fun <T : Value<T>> Value<T>.set(value: Value<T>): Value<Action> =
  Action(ModelVoid.Set(model as ModelVariable<ModelVoid>, value.model))

fun <T : Value<T>> Value<T>.capture(value: Value<T>): Value<Action> =
  Action(ModelVoid.Capture(model as ModelVariable<ModelVoid>, value.model))

val Value<Action>.activity: Value<Activity> get() = Activity(model)

fun sequence(actions: List<Value<Action>>): Value<Action> =
  Action(ModelValue.Sequence(actions.map { it.model }))

fun sequence(action: Value<Action>, vararg actions: Value<Action>): Value<Action> =
  sequence(listOf(action, *actions))

@JvmName("thenAction")
fun Value<Action>.then(action: Value<Action>, vararg actions: Value<Action>): Value<Action> =
  sequence(this, action, *actions)

@JvmName("thenActivity")
fun Value<Action>.then(activity: Value<Activity>, vararg activities: Value<Activity>): Value<Activity> =
  sequence(this.activity, activity, *activities)

