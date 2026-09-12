package micapolos.zexy.compiler

import micapolos.zexy.indexed.Integer
import micapolos.zexy.indexed.Number
import micapolos.zexy.indexed.Value
import micapolos.zexy.runtime.Type

val Value<*>.type: Type
  get() =
    when (this) {
      is Integer -> Type.INT
      is Number -> Type.DOUBLE
      else -> Type.OBJECT
    }