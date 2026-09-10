package micapolos.zexy3

import micapolos.zexy3.model.Number as ModelNumber
import micapolos.zexy3.model.Value as ModelValue

class Number internal constructor(model: ModelNumber): Value<Number>(model)

val Double.value get() = Number(ModelNumber.Constant(this))

internal val <T : Value<T>> Value<T>.modelNumber get() = modelOrChildren as ModelValue<ModelNumber>
internal val Value<Number>.cast get() = this as Number
