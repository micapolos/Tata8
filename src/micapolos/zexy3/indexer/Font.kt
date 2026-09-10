package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Font
import micapolos.zexy3.model.Font as ModelFont

fun Indexer.indexedFont(font: ModelFont): Font =
  when (font) {
    is ModelFont.Resource ->
      Font.Resource(
        font.fileName,
        spaceWidth = font.spaceWidth,
        charSpacing = font.charSpacing,
        lineSpacing = font.lineSpacing
      )
  }
