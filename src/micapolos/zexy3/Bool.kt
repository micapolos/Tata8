package micapolos.zexy3

class Bool internal constructor(internal val integer: Value<Integer>): Value<Bool>(listOf(integer))

internal val Value<Bool>.cast: Bool get() = this as Bool

val Boolean.value: Value<Bool> get() = Bool(if (this) 1.value else 0.value)

val Value<Bool>.integer get() = children[0] as Integer

fun variable(initial: Boolean) = variable(initial.value)

fun variable(initial: Boolean, fn: (Value<Bool>) -> Value<Activity>) =
  variable(initial.value, fn)

infix fun Value<Bool>.and(bool: Value<Bool>) = Bool(integer and bool.integer)

infix fun Value<Bool>.or(bool: Value<Bool>) = Bool(integer or bool.integer)

operator fun Value<Bool>.not() = Bool(integer xor 1)

fun <T: Value<T>> Value<Bool>.selectTrueFalse(trueValue: Value<T>, falseValue: Value<T>): Value<T> =
  integer.selectFrom(falseValue, trueValue)

class IfTrue<T : Value<T>>(val condition: Value<Bool>, val trueCase: Value<T>)
fun <T: Value<T>> Value<Bool>.ifTrue(trueCase: Value<T>) = IfTrue(this, trueCase)
fun <T: Value<T>> IfTrue<T>.orElse(falseCase: Value<T>) = condition.selectTrueFalse(trueCase, falseCase)

val Value<Bool>.changed: Event get() = TODO()