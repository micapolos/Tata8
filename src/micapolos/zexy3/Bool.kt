package micapolos.zexy3

class Bool internal constructor(impl: Any): Value<Bool>(impl)

internal val Value<Bool>.integer get() = children[0] as Value<Integer>

fun bool(b: Boolean): Value<Bool> = Bool(listOf(if (b) 1.value else 0.value))

val Boolean.value: Value<Bool> get() = bool(this)

infix fun Value<Bool>.and(bool: Value<Bool>) = integer and bool.integer

infix fun Value<Bool>.or(bool: Value<Bool>) = integer or bool.integer

operator fun Value<Bool>.not() = integer xor 1

fun <T: Value<T>> Value<Bool>.select(trueValue: Value<T>, falseValue: Value<T>): Value<T> =
  integer.select(trueValue, falseValue)