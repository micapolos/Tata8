package micapolos.zexy3

class Bool internal constructor(internal val integer: Value<Integer>): Value<Bool>(listOf(integer))

val Value<Bool>.cast: Bool get() = this as Bool

val Boolean.value: Value<Bool> get() = Bool(if (this) 1.value else 0.value)

fun variable(initial: Boolean) = animatedVariable(initial.value)

fun animatedVariable(initial: Boolean, fn: (Value<Bool>) -> Value<Action>) =
  animatedVariable(initial.value, fn)

infix fun Value<Bool>.and(bool: Value<Bool>) = Bool(cast.integer and bool.cast.integer)

infix fun Value<Bool>.or(bool: Value<Bool>) = Bool(cast.integer or bool.cast.integer)

operator fun Value<Bool>.not() = Bool(cast.integer xor 1)

fun <T: Value<T>> Value<Bool>.select(trueValue: Value<T>, falseValue: Value<T>): Value<T> =
  cast.integer.select(trueValue, falseValue)