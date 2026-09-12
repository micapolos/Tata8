package micapolos.zexy.indexer

import micapolos.zexy.indexed.Color
import micapolos.zexy.model.Color as ModelColor

fun Indexer.indexedColor(modelColor: ModelColor): Color =
  when (modelColor) {
    is ModelColor.Rgba ->
      Color.Rgba(
        indexed(modelColor.red),
        indexed(modelColor.green),
        indexed(modelColor.blue),
        indexed(modelColor.alpha)
      )
  }
