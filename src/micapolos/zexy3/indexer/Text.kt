package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Text
import micapolos.zexy3.model.Text as ModelText

fun indexed(text: ModelText): Text =
  when (text) {
    is ModelText.Constant -> Text.Constant(text.string)
  }
