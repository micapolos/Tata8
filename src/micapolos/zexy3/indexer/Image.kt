package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Image
import micapolos.zexy3.model.Image as ModelImage

fun Indexer.indexed(model: ModelImage): Image =
  when (model) {
    is ModelImage.Render -> Image.Render(indexed(model.drawing), model.width, model.height)
    is ModelImage.Resource -> Image.Resource(model.fileName)
    is ModelImage.Slice -> Image.Slice(indexed(model.image), model.x, model.y, model.width, model.height)
  }
