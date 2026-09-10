package micapolos.zexy3

import micapolos.zexy3.model.Text as ModelText
import micapolos.zexy3.model.Value as ModelValue

class Text internal constructor(model: Any): Value<Text>(model)

internal val Value<Text>.modelText get() = model as ModelValue<ModelText>

val String.value: Value<Text> get() =
  Text(ModelText.Constant(this))
