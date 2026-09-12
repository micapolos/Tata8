package micapolos.zexy

import micapolos.zexy.model.Font as ModelFont
import micapolos.zexy.model.Integer as ModelInteger
import micapolos.zexy.model.Value as ModelValue

class Font internal constructor(model: Any) : Value<Font>(model)

internal val Value<Font>.modelFont get() = model as ModelValue<ModelFont>

fun font(fileName: String, spaceWidth: Int = 2, charSpacing: Int = 1, lineSpacing: Int = 1) =
  Font(ModelFont.Resource(fileName, spaceWidth, charSpacing, lineSpacing))

fun Value<Font>.width(string: String) = width(string.value)

fun Value<Font>.width(text: Value<Text>): Value<Integer> =
  Integer(ModelInteger.TextWidth(text.modelText, modelFont))

fun Value<Font>.height(string: String) = height(string.value)

fun Value<Font>.height(text: Value<Text>): Value<Integer> =
  Integer(ModelInteger.TextHeight(text.modelText, modelFont))

val allCharsString = "!\"#$%&'()*+,-./0123456789:;<=>?\n" +
    "@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_\n" +
    "`abcdefghijklmnopqrstuvwxyz{|}~"

fun Value<Font>.show() {
  label
    .with(allCharsString)
    .with(position(8, 8))
    .with(this)
    .show()
}