package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Value
import micapolos.zexy3.model.Color as ModelColor
import micapolos.zexy3.model.Value as ModelValue
import micapolos.zexy3.model.Variable as ModelVariable
import micapolos.zexy3.model.Void as ModelVoid

fun <T: Value<T>> Indexer.indexed(modelValue: ModelValue<*>): Value<T> =
  when (modelValue) {
    is ModelVoid -> indexed(modelValue)
    is ModelVariable<*> -> indexed(modelValue)
    is ModelColor -> indexed(modelValue)
    else -> TODO()
  } as Value<T>
