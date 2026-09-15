package micapolos.zexy.indexer

import micapolos.zexy.indexed.IndexType
import micapolos.zexy.model.*
import micapolos.zexy.model.Number

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
      is Value.Logged<*> -> value.indexType
      is Value.Select<*> -> options.first().indexType
      is Variable<*> -> initial.indexType
      is Action -> IndexType.OBJECT
      is Animation -> IndexType.OBJECT
    }