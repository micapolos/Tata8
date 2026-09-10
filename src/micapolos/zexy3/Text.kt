package micapolos.zexy3

import micapolos.zexy3.model.Text as ModelText
import micapolos.zexy3.model.Value as ModelValue

class Text internal constructor(model: Any): Value<Text>(model)

internal val Value<Text>.modelText get() = model as ModelValue<ModelText>

fun variable(initial: String) = animatedVariable(initial.value)
fun animatedVariable(initial: String, fn: (Value<Text>) -> Value<Action>) = animatedVariable(initial.value, fn)

val String.value: Value<Text> get() =
  Text(ModelText.Constant(this))
