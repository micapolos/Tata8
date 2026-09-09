package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Drawing
import micapolos.zexy3.indexed.Drawing.Empty
import micapolos.zexy3.model.Drawing as ModelDrawing

fun Indexer.indexed(model: ModelDrawing): Drawing =
  when (model) {
    ModelDrawing.Empty -> Empty
    ModelDrawing.Rect -> Drawing.Rect
    is ModelDrawing.Label -> Drawing.Label(indexed(model.text))
    is ModelDrawing.Sprite -> TODO()
    is ModelDrawing.Stack -> Drawing.Stack(model.drawings.map { indexed(it) })
    is ModelDrawing.Blend -> Drawing.Blend(indexed(model.drawing), indexed(model.composite))
    is ModelDrawing.Rotate -> Drawing.Rotate(indexed(model.drawing), indexed(model.radians))
    is ModelDrawing.Scale -> Drawing.Scale(indexed(model.drawing), indexed(model.x), indexed(model.y))
    is ModelDrawing.Translate -> Drawing.Translate(indexed(model.drawing), indexed(model.x), indexed(model.y))
    is ModelDrawing.WithColor -> Drawing.WithColor(indexed(model.drawing), indexed(model.color))
    is ModelDrawing.WithFont -> Drawing.WithColor(indexed(model.drawing), indexed(model.font))
  }