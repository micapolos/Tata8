package micapolos.zexy3

import micapolos.zexy3.model.Integer
import micapolos.zexy3.model.Number as ModelNumber
import micapolos.zexy3.model.Value as ModelValue

class Number internal constructor(model: ModelNumber): Value<Number>(model)

val Double.value get() = Number(ModelNumber.Constant(this))

fun variable(initial: Double) = variable(initial.value)
fun variable(initial: Double, fn: (Value<Number>) -> Value<Activity>) = variable(initial.value, fn)

internal val <T : Value<T>> Value<T>.modelNumber get() = modelOrChildren as ModelValue<ModelNumber>
internal val Value<Number>.cast get() = this as Number

internal fun Value<Number>.apply(op2: ModelNumber.Op2, integer: Value<Number>): Value<Number> =
  Number(ModelNumber.Apply2(op2, modelNumber, integer.modelNumber))

operator fun Value<Number>.unaryMinus() = 0.0.value - this

operator fun Value<Number>.plus(d: Double) = plus(d.value)
operator fun Value<Number>.plus(number: Value<Number>) = apply(ModelNumber.Op2.ADD, number)

operator fun Value<Number>.minus(d: Double) = minus(d.value)
operator fun Value<Number>.minus(number: Value<Number>) = apply(ModelNumber.Op2.SUB, number)

operator fun Value<Number>.times(d: Double) = times(d.value)
operator fun Value<Number>.times(number: Value<Number>) = apply(ModelNumber.Op2.MUL, number)

fun Value<Bool>.selectTrueFalse(trueCase: Double, falseCase: Double): Value<Number> =
  selectTrueFalse(trueCase.value, falseCase.value)

fun Value<Bool>.ifTrue(trueCase: Double) = ifTrue(trueCase.value)
fun IfTrue<Number>.orElse(falseCase: Double) = orElse(falseCase.value)

//fun Value<Integer>.selectFrom(cases: List<Double>): Value<Number> =
//  selectFrom(cases.map { it.value })
//
//fun Value<Integer>.selectFrom(firstCast: Double, vararg otherCases: Double): Value<Number> =
//  selectFrom(listOf(firstCast, *otherCases.toTypedArray()))
