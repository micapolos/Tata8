package micapolos.zexy3

import micapolos.zexy3.model.Number as ModelNumber
import micapolos.zexy3.model.Value as ModelValue

class Number internal constructor(model: ModelNumber): Value<Number>(model)

val Double.value get() = Number(ModelNumber.Constant(this))

fun variable(initial: Double) = animatedVariable(initial.value)
fun animatedVariable(initial: Double, fn: (Value<Number>) -> Value<Action>) = animatedVariable(initial.value, fn)

internal val <T : Value<T>> Value<T>.modelNumber get() = modelOrChildren as ModelValue<ModelNumber>
internal val Value<Number>.cast get() = this as Number

fun Value<Bool>.selectTrueFalse(trueCase: Double, falseCase: Double): Value<Number> =
  selectTrueFalse(trueCase.value, falseCase.value)

fun Value<Bool>.ifTrue(trueCase: Double) = ifTrue(trueCase.value)
fun IfTrue<Number>.orElse(falseCase: Double) = orElse(falseCase.value)

fun Value<Integer>.selectTrueFalse(cases: List<Double>): Value<Number> =
  selectTrueFalse(cases.map { it.value })

fun Value<Integer>.selectTrueFalse(firstCast: Double, vararg otherCases: Double): Value<Number> =
  selectTrueFalse(listOf(firstCast, *otherCases.toTypedArray()))

