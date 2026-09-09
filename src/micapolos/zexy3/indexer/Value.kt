package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Value
import micapolos.zexy3.model.Value as ModelValue
import micapolos.zexy3.model.Void as ModelVoid

fun <T: Value<T>> Indexer.indexed(modelValue: ModelValue<*>): Value<T> =
  when (modelValue) {
    is ModelVoid -> indexedVoid(modelValue)
    else -> TODO()
  } as Value<T>
