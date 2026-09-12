package micapolos.zexy.indexer

import micapolos.zexy.indexed.Font
import micapolos.zexy.model.Font as ModelFont

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
