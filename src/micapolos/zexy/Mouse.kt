package micapolos.zexy

import micapolos.zexy.model.Integer as ModelInteger

object Mouse {
  val position = position(
    Integer(ModelInteger.Apply0(ModelInteger.Op0.MOUSE_X)),
    Integer(ModelInteger.Apply0(ModelInteger.Op0.MOUSE_Y))
  )

  val isPressed = Bool(ModelInteger.Apply0(ModelInteger.Op0.MOUSE_DOWN))

  val press get() = isPressed.changeTo(true)

  val release get() = isPressed.changeTo(false)
}

val mouse = Mouse