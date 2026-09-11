package micapolos.zexy3

import micapolos.zexy3.model.Void as ModelVoid

class Activity internal constructor(model: Any) : Value<Activity>(model)

fun pause(seconds: Double) = pause(seconds.value)

fun pause(seconds: Value<Number>): Value<Activity> =
  Activity(ModelVoid.Pause(seconds.modelNumber))

fun <T : Value<T>> parallel(vararg values: Value<T>): Value<Activity> =
  Activity(ModelVoid.Parallel(values.map { it.model }))

fun sequence(activities: List<Value<Activity>>): Value<Activity> =
  Activity(ModelVoid.Parallel(activities.map { it.model }))

fun sequence(activity: Value<Activity>, vararg activities: Value<Activity>): Value<Activity> =
  sequence(listOf(activity, *activities))

fun Value<Activity>.then(activity: Value<Activity>, vararg activities: Value<Activity>): Value<Activity> =
  sequence(this, activity, *activities)
