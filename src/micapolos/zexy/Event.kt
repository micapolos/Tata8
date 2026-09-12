package micapolos.zexy

class Event internal constructor(model: Any): Value<Event>(model)

val Value<Bool>.occurrence get() = Event(model)

val Value<Event>.isOccurring get() = Bool(model)

fun Value<Event>.and(bool: Value<Bool>): Value<Event> = isOccurring.and(bool).occurrence