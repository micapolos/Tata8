package micapolos.zexy3

import micapolos.zexy3.model.Drawing as ModelDrawing

class Rect(model: Any): Drawing<Rect>(model)

internal val Value<Rect>.modelRect get() = model as ModelDrawing.Rect

val Value<Rect>.x get() = Integer(modelRect.x)
val Value<Rect>.y get() = Integer(modelRect.y)
val Value<Rect>.width get() = Integer(modelRect.width)
val Value<Rect>.height get() = Integer(modelRect.height)

val Value<Rect>.cast get() = this as Rect

val rect = rect(0, 0, 0, 0)

fun rect(x: Int, y: Int, width: Int, height: Int) =
  rect(x.value, y.value, width.value, height.value)

fun rect(x: Value<Integer>, y: Value<Integer>, width: Value<Integer>, height: Value<Integer>) =
  Rect(ModelDrawing.Rect(x.modelInteger, y.modelInteger, width.modelInteger, height.modelInteger))

@JvmName("rectWithPosition")
fun Value<Rect>.with(position: Value<Position>) =
  rect(position.x, position.y, width, height)

@JvmName("rectWithSize")
fun Value<Rect>.with(size: Value<Size>) =
  rect(x, y, size.width, size.height)