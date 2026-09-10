package micapolos.zexy3

import micapolos.zexy3.model.Drawing as ModelDrawing
import micapolos.zexy3.model.Text as ModelText

class Label internal constructor(model: Any) : Value<Label>(model)

internal val Value<Label>.modelLabel get() = model as ModelDrawing.Label

val label = Label(
  ModelDrawing.Label(
    ModelText.Constant(""),
    0.value.cast,
    0.value.cast))

@JvmName("withLabel")
fun Value<Label>.with(text: Value<Text>): Value<Label> =
  Label(ModelDrawing.Label(text.modelText, modelLabel.x, modelLabel.y))

@JvmName("withPosition")
fun Value<Label>.with(positionValue: Value<Position>): Value<Label> =
  Label(
    ModelDrawing.Label(
      modelLabel.text,
      positionValue.position.x.cast,
      positionValue.position.y.cast))