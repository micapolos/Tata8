package micapolos.zexy3

import micapolos.zexy3.model.Drawing as ModelDrawing
import micapolos.zexy3.model.Value as ModelValue

class Drawing internal constructor(model: Any): Value<Drawing>(model)

internal val Value<Drawing>.modelDrawing get() = model as ModelValue<ModelDrawing>

val noDrawing get() = Drawing(ModelDrawing.Empty)

fun rect(x: Int, y: Int, width: Int, height: Int) = rect(x.value, y.value, width.value, height.value)
fun rect(x: Value<Integer>, y: Value<Integer>, width: Value<Integer>, height: Value<Integer>) =
  Drawing(ModelDrawing.Rect(x.modelInteger, y.modelInteger, width.modelInteger, height.modelInteger))

fun sprite(image: Value<Image>, x: Int, y: Int) = sprite(image, x.value, y.value)
fun sprite(image: Value<Image>, x: Value<Integer>, y: Value<Integer>) =
  Drawing(ModelDrawing.Sprite(image.modelImage, x.modelInteger, y.modelInteger))

fun label(string: String, x: Int, y: Int) = label(string.value, x.value, y.value)
fun label(text: Value<Text>, x: Int, y: Int) = label(text, x.value, y.value)
fun label(text: Value<Text>, x: Value<Integer>, y: Value<Integer>) =
  Drawing(ModelDrawing.Label(text.modelText, x.modelInteger, y.modelInteger))

fun stack(vararg drawings: Value<Drawing>) = stack(drawings.toList())
fun stack(drawings: List<Value<Drawing>>) = Drawing(ModelDrawing.Stack(drawings.map { it.modelDrawing }))
