package micapolos.zexy3

import micapolos.zexy3.model.Drawing as ModelDrawing
import micapolos.zexy3.model.Text as ModelText

class Label internal constructor(model: Any) : Drawing<Label>(model)

internal val Value<Label>.modelLabel get() = model as ModelDrawing.Label

val label: Value<Label> = Label(
  ModelDrawing.Label(
    ModelText.Constant(""),
    0.value.cast,
    0.value.cast
  )
)

fun Value<Label>.with(string: String): Value<Label> = with(string.value)

@JvmName("withText")
fun Value<Label>.with(text: Value<Text>): Value<Label> =
  Label(ModelDrawing.Label(text.modelText, modelLabel.x, modelLabel.y))

fun Value<Label>.withPosition(x: Int, y: Int) = withPosition(x.value, y.value)
fun Value<Label>.withPosition(x: Int, y: Value<Integer>) = withPosition(x.value, y)
fun Value<Label>.withPosition(x: Value<Integer>, y: Int) = withPosition(x, y.value)
fun Value<Label>.withPosition(x: Value<Integer>, y: Value<Integer>): Value<Label> =
  Label(
    ModelDrawing.Label(
      modelLabel.text,
      x.modelInteger,
      y.modelInteger
    )
  )

@JvmName("withPosition")
fun Value<Label>.with(positionValue: Value<Position>): Value<Label> =
  Label(
    ModelDrawing.Label(
      modelLabel.text,
      positionValue.position.x.cast,
      positionValue.position.y.cast
    )
  )