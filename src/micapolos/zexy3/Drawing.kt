package micapolos.zexy3

import micapolos.zexy3.model.Drawing as ModelDrawing
import micapolos.zexy3.model.Value as ModelValue

open class Drawing<out T: Drawing<T>> internal constructor(model: Any) : Value<T>(model) {
  object Empty: Drawing<Empty>(ModelDrawing.Empty)
}

internal val <T: Drawing<T>> Value<T>.modelDrawing get() = model as ModelValue<ModelDrawing>

val noDrawing: Value<Drawing.Empty> get() = Drawing.Empty

fun <T: Drawing<T>> stack(vararg drawings: Value<T>) = stack(drawings.toList())

fun <T: Drawing<T>> stack(drawings: List<Value<T>>): Value<T> =
  Drawing(ModelDrawing.Stack(drawings.map { it.modelDrawing }))

@JvmName("drawingWithFont")
fun <T: Drawing<T>> Value<T>.with(font: Value<Font>): Value<T> =
  Drawing(ModelDrawing.WithFont(modelDrawing, font.modelFont))

@JvmName("drawingWithColor")
fun <T: Drawing<T>> Value<T>.with(color: Value<Color>): Value<T> =
  Drawing(ModelDrawing.WithColor(modelDrawing, color.modelColor))

@JvmName("drawingWith")
fun <T: Drawing<T>> Value<T>.with(composite: Composite): Value<T> =
  Drawing(ModelDrawing.WithComposite(modelDrawing, composite.model))

fun <T: Drawing<T>> Value<T>.show() {
  game.with(this).show()
}