package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Drawing
import micapolos.zexy3.indexed.Drawing.Empty
import micapolos.zexy3.model.Drawing as ModelDrawing

fun Indexer.indexedDrawing(model: ModelDrawing): Drawing =
  when (model) {
    ModelDrawing.Empty -> Empty
    is ModelDrawing.Rect -> Drawing.Rect(indexed(model.x), indexed(model.y), indexed(model.width), indexed(model.height))
    is ModelDrawing.Label -> Drawing.Label(indexed(model.text), indexed(model.x), indexed(model.y))
    is ModelDrawing.Sprite -> Drawing.Sprite(indexed(model.image), indexed(model.x), indexed(model.y))
    is ModelDrawing.WithComposite -> Drawing.WithComposite(indexed(model.drawing), model.composite.indexed)
    is ModelDrawing.WithColor -> Drawing.WithColor(indexed(model.drawing), indexed(model.color))
    is ModelDrawing.WithFont -> Drawing.WithFont(indexed(model.drawing), indexed(model.font))
    is ModelDrawing.Stack -> Drawing.Stack(model.drawings.map { indexed(it) })
  }