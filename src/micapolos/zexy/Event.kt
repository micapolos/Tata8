package micapolos.zexy

import micapolos.zexy.model.Value as ModelValue

class Event internal constructor(model: ModelValue<*>): ValueWithModel<Event>(model)

internal val Value<Event>.modelInteger get() = isOccurring.integer.modelInteger

val Value<Bool>.occurrence get() = Event(model)

val Value<Event>.isOccurring get() = Bool(model)

fun Value<Event>.and(bool: Value<Bool>): Value<Event> = isOccurring.and(bool).occurrence