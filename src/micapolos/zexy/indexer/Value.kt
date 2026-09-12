package micapolos.zexy.indexer

import micapolos.zexy.indexed.Value
import micapolos.zexy.indexed.Value.*
import micapolos.zexy.model.Color as ModelColor
import micapolos.zexy.model.Drawing as ModelDrawing
import micapolos.zexy.model.Font as ModelFont
import micapolos.zexy.model.Image as ModelImage
import micapolos.zexy.model.Integer as ModelInteger
import micapolos.zexy.model.Number as ModelNumber
import micapolos.zexy.model.Text as ModelText
import micapolos.zexy.model.Value as ModelValue
import micapolos.zexy.model.Variable as ModelVariable
import micapolos.zexy.model.Void as ModelVoid

fun <T: Value<T>> Indexer.indexed(model: ModelValue<*>): Value<T> =
  when (model) {
    is ModelVoid -> indexedVoid(model)
    is ModelVariable<*> -> indexedVariable(model)
    is ModelColor -> indexedColor(model)
    is ModelFont -> indexedFont(model)
    is ModelImage -> indexedImage(model)
    is ModelText -> indexedText(model)
    is ModelInteger -> indexedInteger(model)
    is ModelNumber -> indexedNumber(model)
    is ModelDrawing -> indexedDrawing(model)

    is ModelValue.Logged<*> -> Logged(model.label, indexed(model.value))
    is ModelValue.Sequence<*> -> Sequence(model.values.map { indexed(it) })
    is ModelValue.Select<*> -> Select(indexed(model.index), model.options.map { indexed(it) })
    is ModelValue.RunWhile<*> -> RunWhile(indexed(model.condition), indexed(model.value))
    is ModelValue.RepeatWhile<*> -> RepeatWhile(indexed(model.condition), indexed(model.value))
    is ModelValue.StartWhen<*> -> StartWhen(indexed(model.condition), indexed(model.value))
    is ModelValue.Stretch<*> -> Stretch(indexed(model.factor), indexed(model.value))
    is ModelValue.Stateful<*> -> Stateful(indexed(model.state), indexed(model.value))
    is ModelValue.Race<*> -> Race(indexed(model.value), model.others.map { indexed(it) })
    is ModelValue.EveryFrame<*> -> EveryFrame(indexed(model.value))
    is ModelValue.NextFrame<*> -> NextFrame(indexed(model.value))
  } as Value<T>
