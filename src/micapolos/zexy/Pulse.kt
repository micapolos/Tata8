package micapolos.zexy

import micapolos.zexy.model.Value as ModelValue

class Pulse<T: Value<T>> internal constructor(impl: Any?): Value<Pulse<T>>(impl)

fun <T: Value<T>> Value<T>.pulse(high: Value<T>): Pulse<T> =
  Pulse(ModelValue.Pulse(high.model, model))
