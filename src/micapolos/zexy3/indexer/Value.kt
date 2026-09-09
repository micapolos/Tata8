package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Value
import micapolos.zexy3.model.Color as ModelColor
import micapolos.zexy3.model.Composite as ModelComposite
import micapolos.zexy3.model.Font as ModelFont
import micapolos.zexy3.model.Image as ModelImage
import micapolos.zexy3.model.Text as ModelText
import micapolos.zexy3.model.Value as ModelValue
import micapolos.zexy3.model.Variable as ModelVariable
import micapolos.zexy3.model.Void as ModelVoid

fun <T: Value<T>> Indexer.indexed(value: ModelValue<*>): Value<T> =
  when (value) {
    is ModelVoid -> indexed(value)
    is ModelVariable<*> -> indexed(value)
    is ModelColor -> indexed(value)
    is ModelComposite -> indexed(value)
    is ModelFont -> indexed(value)
    is ModelImage -> indexed(value)
    is ModelText -> indexed(value)
    else -> TODO()
  } as Value<T>
