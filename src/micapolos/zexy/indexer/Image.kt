package micapolos.zexy.indexer

import micapolos.zexy.indexed.Image
import micapolos.zexy.indexed.Image.*
import micapolos.zexy.model.Image as ModelImage

fun Indexer.indexedImage(model: ModelImage): Image =
  when (model) {
    ModelImage.Empty -> Empty
    is ModelImage.Resource -> Resource(model.fileName)
  }
