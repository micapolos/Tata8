package micapolos.zexy3

import micapolos.zexy3.model.Integer as ModelInteger

class Mouse internal constructor(position: Value<Position>) : Value<Mouse>(listOf(position))

val Value<Mouse>.position: Value<Position> get() = children[0] as Value<Position>

val mouse =
  Mouse(
    position(
      Integer(ModelInteger.Apply0(ModelInteger.Op0.MOUSE_X)),
      Integer(ModelInteger.Apply0(ModelInteger.Op0.MOUSE_Y))
    )
  )