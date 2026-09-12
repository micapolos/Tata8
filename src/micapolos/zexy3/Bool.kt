package micapolos.zexy3

import micapolos.zexy3.compiler.toInt
import micapolos.zexy3.model.Integer as ModelInteger

class Bool internal constructor(impl: Any): Value<Bool>(impl)

internal val Value<Bool>.cast: Bool get() = this as Bool

val Boolean.value: Value<Bool> get() = Bool(toInt().value.model)

val Value<Bool>.integer get() = Integer(model as ModelInteger)

fun variable(initial: Boolean) = variable(initial.value)

fun variable(initial: Boolean, fn: (Value<Bool>) -> Value<Activity>) =
  variable(initial.value, fn)

infix fun Value<Bool>.and(bool: Boolean) = and(bool.value)
infix fun Value<Bool>.and(bool: Value<Bool>) = Bool(integer.and(bool.integer).model)

infix fun Value<Bool>.or(bool: Boolean) = or(bool.value)
infix fun Value<Bool>.or(bool: Value<Bool>) = Bool(integer.or(bool.integer).model)

infix fun Value<Bool>.xor(bool: Boolean) = xor(bool.value)
infix fun Value<Bool>.xor(bool: Value<Bool>) = Bool(integer.xor(bool.integer).model)

operator fun Value<Bool>.not() = Bool(integer.xor(1).model)

fun <T: Value<T>> Value<Bool>.selectTrueFalse(trueValue: Value<T>, falseValue: Value<T>): Value<T> =
  integer.selectFrom(falseValue, trueValue)

class IfTrue<T : Value<T>>(val condition: Value<Bool>, val trueCase: Value<T>)
fun <T: Value<T>> Value<Bool>.ifTrue(trueCase: Value<T>) = IfTrue(this, trueCase)
fun <T: Value<T>> IfTrue<T>.orElse(falseCase: Value<T>) = condition.selectTrueFalse(trueCase, falseCase)

val Value<Bool>.change: Event
  get() =
    Event(variable(false).let { variable ->
      xor(variable).also {
        variable.capture(this).everyFrame
      }
    })
