package micapolos.zexy3

import micapolos.zexy3.model.Void as ModelVoid

class Void internal constructor(model: ModelVoid): Value<Void>(model)

fun pause(seconds: Value<Number>): Value<Void> =
  Void(ModelVoid.Pause(seconds.modelNumber))

fun <T: Value<T>> parallel(vararg values: Value<T>): Value<Void> =
  Void(ModelVoid.Parallel(values.map { it.model }))

fun <T: Value<T>> Variable<T>.set(value: Value<T>): Value<Void> =
  Void(ModelVoid.Set(erasedModelVariable, value.model))
