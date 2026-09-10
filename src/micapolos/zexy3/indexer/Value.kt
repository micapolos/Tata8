package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Value
import micapolos.zexy3.model.Color as ModelColor
import micapolos.zexy3.model.Drawing as ModelDrawing
import micapolos.zexy3.model.Font as ModelFont
import micapolos.zexy3.model.Image as ModelImage
import micapolos.zexy3.model.Integer as ModelInteger
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
    is ModelFont -> indexed(model)
    is ModelImage -> indexed(model)
    is ModelText -> indexed(model)
    is ModelInteger -> indexed(model)
    is ModelNumber -> indexed(model)
    is ModelDrawing -> indexed(model)

    is ModelValue.Logged<*> -> Value.Logged(model.label, indexed(model.value))
    is ModelValue.Sequence<*> -> Value.Sequence(model.values.map { indexed(it) })
    is ModelValue.Select<*> -> Value.Select(indexed(model.index), model.options.map { indexed(it) })
    is ModelValue.RunWhile<*> -> Value.RunWhile(indexed(model.condition), indexed(model.value))
    is ModelValue.StartWhen<*> -> Value.StartWhen(indexed(model.condition), indexed(model.value))
    is ModelValue.Stretch<*> -> Value.Stretch(indexed(model.factor), indexed(model.value))
    is ModelValue.Stateful<*> -> Value.Stateful(indexed(model.state), indexed(model.value))
    is ModelValue.Race<*> -> Value.Race(indexed(model.value), model.others.map { indexed(it) })
    is ModelValue.Frame<*> -> Value.Frame(indexed(model.value))
  } as Value<T>
