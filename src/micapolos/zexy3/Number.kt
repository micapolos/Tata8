package micapolos.zexy3

import micapolos.zexy3.model.Number as ModelNumber

class Number internal constructor(model: ModelNumber): Value<Number>(model)

internal val <T : Value<T>> Value<T>.modelNumber: ModelNumber get() = modelOrChildren as ModelNumber
