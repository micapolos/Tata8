package micapolos.zexy.indexer

import micapolos.zexy.indexed.Text
import micapolos.zexy.model.Text as ModelText

fun Indexer.indexedText(text: ModelText): Text =
  when (text) {
    is ModelText.Constant -> Text.Constant(text.string)
  }
