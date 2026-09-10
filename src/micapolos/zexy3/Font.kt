package micapolos.zexy3

import micapolos.zexy3.model.Font as ModelFont
import micapolos.zexy3.model.Integer as ModelInteger
import micapolos.zexy3.model.Value as ModelValue

class Font internal constructor(model: Any): Value<Font>(model)

internal val Value<Font>.modelFont get() = model as ModelValue<ModelFont>

fun font(fileName: String, spaceWidth: Int, charSpacing: Int, lineSpacing: Int) =
  Font(ModelFont.Resource(fileName, spaceWidth, charSpacing, lineSpacing))

fun Value<Font>.width(text: Value<Text>) = Integer(ModelInteger.TextWidth(text.modelText, modelFont))
fun Value<Font>.height(text: Value<Text>) = Integer(ModelInteger.TextHeight(text.modelText, modelFont))
