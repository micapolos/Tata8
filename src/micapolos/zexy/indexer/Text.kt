package micapolos.zexy.indexer

import micapolos.zexy.indexed.Text
import micapolos.zexy.indexed.Text.*
import micapolos.zexy.model.Text as ModelText

fun Indexer.indexedText(text: ModelText): Text =
  when (text) {
    is ModelText.Constant -> Constant(text.string)
    is ModelText.Slice -> Slice(indexed(text.text), indexed(text.start), indexed(text.length))
    is ModelText.Join -> Join(text.texts.map { indexed(it) })
  }
