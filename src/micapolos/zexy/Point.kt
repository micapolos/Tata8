package micapolos.zexy

import micapolos.zexy.model.Drawing as ModelDrawing
import micapolos.zexy.model.Value as ModelValue

class Point(model: ModelValue<*>): Drawing<Point>(model)

internal val Value<Point>.modelPoint get() = model as ModelDrawing.Point

val Value<Point>.x get() = Integer(modelPoint.x)
val Value<Point>.y get() = Integer(modelPoint.y)

val Value<Point>.cast get() = this as Point

val point = point(0, 0)

fun point(x: Int, y: Int) =
  point(x.value, y.value)

fun point(x: Value<Integer>, y: Value<Integer>) =
  Point(ModelDrawing.Point(x.modelInteger, y.modelInteger))

@JvmName("pointWithPosition")
fun Value<Point>.with(position: Value<Position>) =
  point(position.x, position.y)
