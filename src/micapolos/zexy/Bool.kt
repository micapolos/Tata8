package micapolos.zexy

import micapolos.zexy.compiler.toInt
import micapolos.zexy.model.Value as ModelValue

class Bool internal constructor(impl: Any): Value<Bool>(impl)

internal val Value<Bool>.bool: Bool get() = this as Bool

val Boolean.value: Value<Bool> get() = Bool(toInt().value.model)

val Value<Bool>.integer: Value<Integer> get() = Integer(model)

fun variable(initial: Boolean): Variable<Bool> = variable(initial.value)

fun Value<Bool>.isEqualTo(bool: Boolean) = isEqualTo(bool.value)
fun Value<Bool>.isEqualTo(bool: Value<Bool>) = integer.isEqualTo(bool.integer)

infix fun Value<Bool>.and(bool: Boolean) = and(bool.value)
infix fun Value<Bool>.and(bool: Value<Bool>) = integer.and(bool.integer).bool

infix fun Value<Bool>.or(bool: Boolean) = or(bool.value)
infix fun Value<Bool>.or(bool: Value<Bool>) = integer.or(bool.integer).bool

infix fun Value<Bool>.xor(bool: Boolean) = xor(bool.value)
infix fun Value<Bool>.xor(bool: Value<Bool>) = integer.xor(bool.integer).bool

operator fun Value<Bool>.not() = integer.xor(1).bool

fun <T: Value<T>> Value<Bool>.selectTrueFalse(trueValue: Value<T>, falseValue: Value<T>): Value<T> =
  integer.selectFrom(falseValue, trueValue)

class IfTrue<T : Value<T>>(val condition: Value<Bool>, val trueCase: Value<T>)
fun <T: Value<T>> Value<Bool>.ifTrue(trueCase: Value<T>) = IfTrue(this, trueCase)
fun <T: Value<T>> IfTrue<T>.orElse(falseCase: Value<T>) = condition.selectTrueFalse(trueCase, falseCase)

val Value<Bool>.logged: Value<Bool> get() = loggedAs(null)

fun Value<Bool>.loggedAs(label: String?): Value<Bool> =
  also { it.ifTrue("true".value).orElse("false".value).loggedAs(label) }

fun Value<Bool>.show() {
  noDrawing.also { logged }.show()
}

context(block: Animation.Block)
fun startWhen(condition: Value<Bool>, fn: Animation.Block.() -> Unit) {
  with(block) {
    condition.integer.selectStart {
      sequence {}
      sequence { fn() }
    }
  }
}
