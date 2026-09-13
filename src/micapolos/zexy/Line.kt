package micapolos.zexy

import micapolos.zexy.model.Drawing as ModelDrawing

class Line(model: Any): Drawing<Line>(model)

internal val Value<Line>.modelLine get() = model as ModelDrawing.Line

val Value<Line>.x1 get() = Integer(modelLine.x1)
val Value<Line>.y1 get() = Integer(modelLine.y1)
val Value<Line>.x2 get() = Integer(modelLine.x2)
val Value<Line>.y2 get() = Integer(modelLine.y2)

val line = line(0, 0, 0, 0)

fun line(x1: Int, y1: Int, x2: Int, y2: Int) =
  line(x1.value, y1.value, x2.value, y2.value)

fun line(x1: Value<Integer>, y1: Value<Integer>, x2: Value<Integer>, y2: Value<Integer>) =
  Line(ModelDrawing.Line(x1.modelInteger, y1.modelInteger, x2.modelInteger, y2.modelInteger))

@JvmName("lineWithStartPosition")
fun Value<Line>.withStart(position: Value<Position>) =
  line(position.x, position.y, x2, y2)

@JvmName("lineWithEndPosition")
fun Value<Line>.withEnd(position: Value<Position>) =
  line(x1, y1, position.x, position.y)