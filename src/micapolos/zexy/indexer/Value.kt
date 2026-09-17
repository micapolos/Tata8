package micapolos.zexy.indexer

import micapolos.zexy.indexed.Value
import micapolos.zexy.indexed.Value.Logged
import micapolos.zexy.indexed.Value.Select
import micapolos.zexy.model.Action as ModelAction
import micapolos.zexy.model.Animation as ModelAnimation
import micapolos.zexy.model.Color as ModelColor
import micapolos.zexy.model.Drawing as ModelDrawing
import micapolos.zexy.model.Font as ModelFont
import micapolos.zexy.model.Image as ModelImage
import micapolos.zexy.model.Integer as ModelInteger
import micapolos.zexy.model.Number as ModelNumber
import micapolos.zexy.model.Text as ModelText
import micapolos.zexy.model.Value as ModelValue
import micapolos.zexy.model.Variable as ModelVariable

fun Indexer.indexed(model: ModelValue): Value =
  when (model) {
    is ModelVariable -> indexedVariable(model)
    is ModelColor -> indexedColor(model)
    is ModelFont -> indexedFont(model)
    is ModelImage -> indexedImage(model)
    is ModelText -> indexedText(model)
    is ModelInteger -> indexedInteger(model)
    is ModelNumber -> indexedNumber(model)
    is ModelDrawing -> indexedDrawing(model)
    is ModelAction -> indexedAction(model)
    is ModelAnimation -> indexedAnimation(model)

    is ModelValue.Logged -> Logged(model.label, indexed(model.value))
    is ModelValue.Select -> Select(indexed(model.index), model.options.map { indexed(it) })
  }
