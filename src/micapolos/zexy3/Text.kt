package micapolos.zexy3

import micapolos.zexy3.model.Text as ModelText

class Text(model: Any): Value<Text>(model)

internal val Value<Text>.modelText get() = model as ModelText

val String.value get() = Text(ModelText.Constant(this))
