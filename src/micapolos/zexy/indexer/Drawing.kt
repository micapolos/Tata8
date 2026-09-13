package micapolos.zexy.indexer

import micapolos.zexy.indexed.Drawing
import micapolos.zexy.indexed.Drawing.Empty
import micapolos.zexy.model.Drawing as ModelDrawing

fun Indexer.indexedDrawing(model: ModelDrawing): Drawing =
  when (model) {
    ModelDrawing.Empty -> Empty
    is ModelDrawing.Point -> Drawing.Point(indexed(model.x), indexed(model.y))
    is ModelDrawing.Line -> Drawing.Line(indexed(model.x1), indexed(model.y1), indexed(model.x2), indexed(model.y2))
    is ModelDrawing.Rect -> Drawing.Rect(indexed(model.x), indexed(model.y), indexed(model.width), indexed(model.height))
    is ModelDrawing.Label -> Drawing.Label(indexed(model.text), indexed(model.x), indexed(model.y))
    is ModelDrawing.Sprite -> Drawing.Sprite(indexed(model.x), indexed(model.y), indexed(model.width), indexed(model.height), indexed(model.image), indexed(model.imageX), indexed(model.imageY))
    is ModelDrawing.WithComposite -> Drawing.WithComposite(indexed(model.drawing), model.composite.indexed)
    is ModelDrawing.WithColor -> Drawing.WithColor(indexed(model.drawing), indexed(model.color))
    is ModelDrawing.WithFont -> Drawing.WithFont(indexed(model.drawing), indexed(model.font))
    is ModelDrawing.Stack -> Drawing.Stack(model.drawings.map { indexed(it) })
    is ModelDrawing.WithClip -> Drawing.WithClip(indexed(model.drawing), indexed(model.x), indexed(model.y), indexed(model.width), indexed(model.height))
  }