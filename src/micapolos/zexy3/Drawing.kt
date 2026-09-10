package micapolos.zexy3

import micapolos.zexy3.model.Drawing.Sprite
import micapolos.zexy3.model.Drawing as ModelDrawing
import micapolos.zexy3.model.Value as ModelValue

open class Drawing<T: Drawing<T>> internal constructor(model: Any) : Value<T>(model) {
  object Empty: Drawing<Empty>(ModelDrawing.Empty)
}

internal val <T: Drawing<T>> Value<T>.modelDrawing get() = model as ModelValue<ModelDrawing>

val noDrawing: Value<Drawing.Empty> get() = Drawing.Empty

fun rect(x: Int, y: Int, width: Int, height: Int) = rect(x.value, y.value, width.value, height.value)
fun rect(x: Value<Integer>, y: Value<Integer>, width: Value<Integer>, height: Value<Integer>) =
  Drawing(ModelDrawing.Rect(x.cast, y.cast, width.cast, height.cast))

fun sprite(image: Value<Image>, x: Int, y: Int) = sprite(image, x.value, y.value)
fun sprite(image: Value<Image>, x: Value<Integer>, y: Value<Integer>) =
  Drawing(Sprite(image.modelImage, x.cast, y.cast))

fun label(string: String, x: Int, y: Int) = label(string.value, x.value, y.value)
fun label(text: Value<Text>, x: Int, y: Int) = label(text, x.value, y.value)
fun label(text: Value<Text>, x: Value<Integer>, y: Value<Integer>) =
  Drawing(ModelDrawing.Label(text.modelText, x.cast, y.cast))

fun stack(vararg drawings: Drawing<*>) = stack(drawings.toList())
fun stack(drawings: List<Drawing<*>>) = Drawing(ModelDrawing.Stack(drawings.map { it.modelDrawing }))

fun Drawing<*>.show() {
  game.with(this).show()
}