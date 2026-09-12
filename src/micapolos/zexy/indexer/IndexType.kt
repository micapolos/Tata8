package micapolos.zexy.indexer

import micapolos.zexy.indexed.IndexType
import micapolos.zexy.model.Color
import micapolos.zexy.model.Drawing
import micapolos.zexy.model.Font
import micapolos.zexy.model.Image
import micapolos.zexy.model.Integer
import micapolos.zexy.model.Number
import micapolos.zexy.model.Text
import micapolos.zexy.model.Value
import micapolos.zexy.model.Variable
import micapolos.zexy.model.Void

val <T : Value<T>> Value<T>.indexType: IndexType
  get() =
    when (this) {
      is Color -> IndexType.OBJECT
      is Drawing -> IndexType.OBJECT
      is Font -> IndexType.OBJECT
      is Image -> IndexType.OBJECT
      is Integer -> IndexType.INTEGER
      is Number -> IndexType.NUMBER
      is Text -> IndexType.OBJECT
      is Value.EveryFrame<*> -> value.indexType
      is Value.NextFrame<*> -> value.indexType
      is Value.Logged<*> -> value.indexType
      is Value.Race<*> -> value.indexType
      is Value.RunWhile<*> -> value.indexType
      is Value.RepeatWhile<*> -> value.indexType
      is Value.Select<*> -> options.first().indexType
      is Value.Sequence<*> -> values.first().indexType
      is Value.StartWhen<*> -> value.indexType
      is Value.Stateful<*> -> value.indexType
      is Value.Stretch<*> -> value.indexType
      is Variable<*> -> initial.indexType
      is Void -> IndexType.OBJECT
    }