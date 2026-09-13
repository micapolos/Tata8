package micapolos.zexy

import micapolos.zexy.model.Text as ModelText
import micapolos.zexy.model.Value as ModelValue

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

fun join(texts: List<Value<Text>>) =
  Text(ModelText.Join(texts.map { it.modelText }))

fun join(text: Value<Text>, vararg texts: Value<Text>) =
 join(listOf(text, *texts))

fun String.slice(start: Int, length: Value<Integer>) = value.slice(start, length)
fun String.slice(start: Value<Integer>, length: Int) = value.slice(start, length)
fun String.slice(start: Value<Integer>, length: Value<Integer>) = value.slice(start, length)

fun Value<Text>.slice(start: Int, length: Int) = slice(start.value, length.value)
fun Value<Text>.slice(start: Int, length: Value<Integer>) = slice(start.value, length)
fun Value<Text>.slice(start: Value<Integer>, length: Int) = slice(start, length.value)
fun Value<Text>.slice(start: Value<Integer>, length: Value<Integer>) =
  Text(ModelText.Slice(modelText, start.modelInteger, length.modelInteger))
