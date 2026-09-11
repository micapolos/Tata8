package micapolos.zexy3

import micapolos.zexy3.model.Drawing as ModelDrawing

class Rect(model: Any): Drawing<Rect>(model) {
  val x get() = Integer(modelRect.x)
  val y get() = Integer(modelRect.x)
  val width get() = Integer(modelRect.x)
  val height get() = Integer(modelRect.x)

  internal val modelRect get() = model as ModelDrawing.Rect
}

fun rect(x: Int, y: Int, width: Int, height: Int) =
  rect(x.value, y.value, width.value, height.value)

fun rect(x: Value<Integer>, y: Value<Integer>, width: Value<Integer>, height: Value<Integer>) =
  Rect(ModelDrawing.Rect(x.modelInteger, y.modelInteger, width.modelInteger, height.modelInteger))