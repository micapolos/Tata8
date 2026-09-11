package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Image
import micapolos.zexy3.indexed.Image.*
import micapolos.zexy3.model.Image as ModelImage

fun Indexer.indexedImage(model: ModelImage): Image =
  when (model) {
    ModelImage.Empty -> Empty
    is ModelImage.Resource -> Resource(model.fileName)
  }
