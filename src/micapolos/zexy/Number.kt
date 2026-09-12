package micapolos.zexy

import micapolos.zexy.model.Number as ModelNumber
import micapolos.zexy.model.Value as ModelValue

class Number internal constructor(model: ModelNumber) : Value<Number>(model)

val Double.value get() = Number(ModelNumber.Constant(this))

fun variable(initial: Double) = variable(initial.value)
fun variable(initial: Double, fn: (Value<Number>) -> Value<Activity>) = variable(initial.value, fn)

internal val <T : Value<T>> Value<T>.modelNumber get() = modelOrChildren as ModelValue<ModelNumber>
internal val Value<Number>.cast get() = this as Number

internal fun Value<Number>.apply(op1: ModelNumber.Op1): Value<Number> =
  Number(ModelNumber.Apply1(op1, modelNumber))

internal fun Value<Number>.apply(op2: ModelNumber.Op2, number: Value<Number>): Value<Number> =
  Number(ModelNumber.Apply2(op2, modelNumber, number.modelNumber))

operator fun Value<Number>.unaryMinus() = apply(ModelNumber.Op1.NEG)

operator fun Value<Number>.plus(d: Double) = plus(d.value)
operator fun Value<Number>.plus(number: Value<Number>) = apply(ModelNumber.Op2.ADD, number)

operator fun Value<Number>.minus(d: Double) = minus(d.value)
operator fun Value<Number>.minus(number: Value<Number>) = apply(ModelNumber.Op2.SUB, number)

operator fun Value<Number>.times(d: Double) = times(d.value)
operator fun Value<Number>.times(number: Value<Number>) = apply(ModelNumber.Op2.MUL, number)

fun sin(number: Double) = sin(number.value)
fun sin(number: Value<Number>) = number.apply(ModelNumber.Op1.SIN)

fun cos(number: Double) = cos(number.value)
fun cos(number: Value<Number>) = number.apply(ModelNumber.Op1.COS)

fun abs(number: Double) = abs(number.value)
fun abs(number: Value<Number>) = number.apply(ModelNumber.Op1.ABS)

fun ceil(number: Double) = ceil(number.value)
fun ceil(number: Value<Number>) = number.apply(ModelNumber.Op1.CEIL)

fun floor(number: Double) = floor(number.value)
fun floor(number: Value<Number>) = number.apply(ModelNumber.Op1.FLOOR)

fun round(number: Double) = round(number.value)
fun round(number: Value<Number>) = number.apply(ModelNumber.Op1.ROUND)

fun fract(number: Double) = fract(number.value)
fun fract(number: Value<Number>) = number.apply(ModelNumber.Op1.FRACT)

fun sqrt(number: Double) = sqrt(number.value)
fun sqrt(number: Value<Number>) = number.apply(ModelNumber.Op1.SQRT)

fun Value<Bool>.selectTrueFalse(trueCase: Double, falseCase: Double): Value<Number> =
  selectTrueFalse(trueCase.value, falseCase.value)

fun Value<Bool>.ifTrue(trueCase: Double) = ifTrue(trueCase.value)
fun IfTrue<Number>.orElse(falseCase: Double) = orElse(falseCase.value)

//fun Value<Integer>.selectFrom(cases: List<Double>): Value<Number> =
//  selectFrom(cases.map { it.value })
//
//fun Value<Integer>.selectFrom(firstCast: Double, vararg otherCases: Double): Value<Number> =
//  selectFrom(listOf(firstCast, *otherCases.toTypedArray()))
