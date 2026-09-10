package micapolos.zexy3

import micapolos.zexy3.model.Drawing as ModelDrawing
import micapolos.zexy3.model.Text as ModelText

class Label internal constructor(model: Any) : Value<Label>(model)

internal val Value<Label>.modelLabel get() = model as ModelDrawing.Label

val label = Label(
  ModelDrawing.Label(
    ModelText.Constant(""),
    0.value.modelInteger,
    0.value.modelInteger))

fun Value<Label>.with(text: Value<Text>): Value<Label> =
  Label(ModelDrawing.Label(text.modelText, modelLabel.x, modelLabel.y))

fun Value<Label>.with(positionValue: Value<Position>): Value<Label> =
  Label(
    ModelDrawing.Label(
      modelLabel.text,
      positionValue.position.x.modelInteger,
      positionValue.position.y.modelInteger))