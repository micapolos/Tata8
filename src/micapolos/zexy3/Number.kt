package micapolos.zexy3

import micapolos.zexy3.model.Number as ModelNumber
import micapolos.zexy3.model.Value as ModelValue

class Number internal constructor(model: ModelNumber): Value<Number>(model)

internal val <T : Value<T>> Value<T>.modelNumber get() = modelOrChildren as ModelValue<ModelNumber>
