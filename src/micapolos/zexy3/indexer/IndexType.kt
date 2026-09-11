package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.IndexType
import micapolos.zexy3.model.Color
import micapolos.zexy3.model.Drawing
import micapolos.zexy3.model.Font
import micapolos.zexy3.model.Image
import micapolos.zexy3.model.Integer
import micapolos.zexy3.model.Number
import micapolos.zexy3.model.Text
import micapolos.zexy3.model.Value
import micapolos.zexy3.model.Variable
import micapolos.zexy3.model.Void

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
      is Value.Frame<*> -> value.indexType
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