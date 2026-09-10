package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Image
import micapolos.zexy3.indexed.Image.*
import micapolos.zexy3.model.Image as ModelImage

fun Indexer.indexedImage(model: ModelImage): Image =
  when (model) {
    ModelImage.Empty -> Empty
    is ModelImage.Render -> Render(indexed(model.drawing), model.width, model.height)
    is ModelImage.Resource -> Resource(model.fileName)
    is ModelImage.Slice -> Slice(indexed(model.image), model.x, model.y, model.width, model.height)
  }
