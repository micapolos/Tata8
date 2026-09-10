package micapolos.zexy3

import micapolos.zexy3.model.Value as ModelValue
import micapolos.zexy3.model.Void as ModelVoid

class Activity internal constructor(model: Any): Value<Activity>(model)

fun pause(seconds: Value<Number>): Value<Activity> =
  Activity(ModelVoid.Pause(seconds.modelNumber))

fun <T: Value<T>> parallel(vararg values: Value<T>): Value<Activity> =
  Activity(ModelVoid.Parallel(values.map { it.model }))

fun <T: Value<T>> Variable<T>.set(value: Value<T>): Value<Activity> =
  Activity(ModelVoid.Set(erasedModelVariable, value.model))
