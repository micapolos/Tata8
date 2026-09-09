package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Value
import micapolos.zexy3.model.Color as ModelColor
import micapolos.zexy3.model.Composite as ModelComposite
import micapolos.zexy3.model.Drawing as ModelDrawing
import micapolos.zexy3.model.Font as ModelFont
import micapolos.zexy3.model.Image as ModelImage
import micapolos.zexy3.model.Integer as ModelInteger
import micapolos.zexy3.model.Key as ModelKey
import micapolos.zexy3.model.Number as ModelNumber
import micapolos.zexy3.model.Text as ModelText
import micapolos.zexy3.model.Value as ModelValue
import micapolos.zexy3.model.Variable as ModelVariable
import micapolos.zexy3.model.Void as ModelVoid

fun <T: Value<T>> Indexer.indexed(model: ModelValue<*>): Value<T> =
  when (model) {
    is ModelVoid -> indexed(model)
    is ModelVariable<*> -> indexed(model)
    is ModelColor -> indexed(model)
    is ModelComposite -> indexed(model)
    is ModelFont -> indexed(model)
    is ModelImage -> indexed(model)
    is ModelText -> indexed(model)
    is ModelKey -> indexed(model)
    is ModelInteger -> indexed(model)
    is ModelNumber -> indexed(model)
    is ModelDrawing -> indexed(model)

    is ModelValue.Capture<*> -> Value.Capture(indexed(model.value))
    is ModelValue.Logged<*> -> Value.Logged(model.label, indexed(model.value))
    is ModelValue.Parallel<*> -> Value.Parallel(model.background.map { indexed(it) }, indexed(model.result))
    is ModelValue.Sequence<*> -> Value.Sequence(model.preceding.map { indexed(it) }, indexed(model.result))
    is ModelValue.Select<*> -> Value.Select(indexed(model.index), model.options.map { indexed(it) })
    is ModelValue.RunWhile<*> -> Value.RunWhile(indexed(model.condition), indexed(model.value))
    is ModelValue.StartWhen<*> -> Value.StartWhen(indexed(model.condition), indexed(model.value))
    is ModelValue.Stretch<*> -> Value.Stretch(indexed(model.factor), indexed(model.value))
  } as Value<T>
