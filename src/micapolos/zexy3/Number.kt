package micapolos.zexy3

import micapolos.zexy3.model.Number as ModelNumber
import micapolos.zexy3.model.Value as ModelValue

class Number internal constructor(model: ModelNumber): Value<Number>(model)

val Double.value get() = Number(ModelNumber.Constant(this))

fun variable(initial: Double) = animatedVariable(initial.value)
fun animatedVariable(initial: Double, fn: (Value<Number>) -> Value<Action>) = animatedVariable(initial.value, fn)

internal val <T : Value<T>> Value<T>.modelNumber get() = modelOrChildren as ModelValue<ModelNumber>
internal val Value<Number>.cast get() = this as Number

fun Value<Bool>.select(trueCase: Double, falseCase: Double): Value<Number> =
  select(trueCase.value, falseCase.value)

fun Value<Integer>.select(cases: List<Double>): Value<Number> =
  select(cases.map { it.value })

fun Value<Integer>.select(firstCast: Double, vararg otherCases: Double): Value<Number> =
  select(listOf(firstCast, *otherCases.toTypedArray()))

