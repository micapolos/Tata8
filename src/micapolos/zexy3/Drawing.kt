package micapolos.zexy3

import micapolos.zexy3.model.Drawing.Sprite as ModelSprite
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

fun label(string: String, x: Int, y: Int) = label(string.value, x.value, y.value)

fun label(text: Value<Text>, x: Int, y: Int) = label(text, x.value, y.value)

fun label(text: Value<Text>, x: Value<Integer>, y: Value<Integer>) =
  Drawing(ModelDrawing.Label(text.modelText, x.cast, y.cast))

fun <T: Drawing<T>> stack(vararg drawings: Value<T>) = stack(drawings.toList())

fun <T: Drawing<T>> stack(drawings: List<Value<T>>) = Drawing(ModelDrawing.Stack(drawings.map { it.modelDrawing }))

fun <T: Drawing<T>> Value<T>.show() {
  game.with(this).show()
}