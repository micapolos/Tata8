package micapolos.zexy3

import micapolos.zexy3.model.Text as ModelText
import micapolos.zexy3.model.Value as ModelValue

class Text internal constructor(model: Any): Value<Text>(model)

internal val Value<Text>.modelText get() = model as ModelValue<ModelText>

fun variable(initial: String) = variable(initial.value)
fun variable(initial: String, fn: (Value<Text>) -> Value<Activity>) = variable(initial.value, fn)

val String.value: Value<Text> get() =
  Text(ModelText.Constant(this))

fun Value<Bool>.selectFrom(trueCase: String, falseCase: String): Value<Text> =
  selectTrueFalse(trueCase.value, falseCase.value)

operator fun List<String>.get(index: Value<Integer>): Value<Text> =
  index.selectFrom(map { it.value })

fun Value<Bool>.ifTrue(trueCase: String) = ifTrue(trueCase.value)
fun IfTrue<Text>.orElse(falseCase: String) = orElse(falseCase.value)

fun Value<Integer>.selectFrom(cases: List<String>): Value<Text> =
  selectFrom(cases.map { it.value })

fun Value<Integer>.selectFrom(firstCast: String, vararg otherCases: String): Value<Text> =
  selectFrom(listOf(firstCast, *otherCases))

