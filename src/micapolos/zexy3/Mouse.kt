package micapolos.zexy3

import micapolos.zexy3.model.Integer as ModelInteger

class Mouse internal constructor(
  val position: Value<Position>,
  val isPressed: Value<Bool>,
) : Value<Mouse>(listOf(position, isPressed))

val mouse =
  Mouse(
    position(
      Integer(ModelInteger.Apply0(ModelInteger.Op0.MOUSE_X)),
      Integer(ModelInteger.Apply0(ModelInteger.Op0.MOUSE_Y))
    ),
    Bool(Integer(ModelInteger.Apply0(ModelInteger.Op0.MOUSE_DOWN))),
  )

val Value<Mouse>.pressed: Event get() = TODO()
val Value<Mouse>.released: Event get() = TODO()
