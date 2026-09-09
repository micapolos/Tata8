package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Color
import micapolos.zexy3.model.Color as ModelColor

fun Indexer.indexedColor(modelColor: ModelColor): Color =
  when (modelColor) {
    is ModelColor.Rgba ->
      Color.Rgba(
        indexed(modelColor.red),
        indexed(modelColor.red),
        indexed(modelColor.red),
        indexed(modelColor.red)
      )
  }
