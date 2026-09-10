package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.Integer
import micapolos.zexy3.indexed.Number
import micapolos.zexy3.indexed.Value
import micapolos.zexy3.runtime.Type

val Value<*>.type: Type
  get() =
    when (this) {
      is Integer -> Type.INT
      is Number -> Type.DOUBLE
      else -> Type.OBJECT
    }