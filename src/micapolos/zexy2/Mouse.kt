package micapolos.zexy2

import micapolos.zexy2.live.Live
import micapolos.zexy2.live.Primitive

object Mouse {
  val position =
    Position<Double>(
      Live.Application(Double::class, Primitive.MOUSE_POSITION_X, listOf()),
      Live.Application(Double::class, Primitive.MOUSE_POSITION_Y, listOf()))

  val isPressed =
    Live.Application<Boolean>(Boolean::class, Primitive.MOUSE_BUTTON_IS_PRESSED, listOf())

  val pressed =
    Live.Application<Boolean>(Boolean::class, Primitive.MOUSE_BUTTON_PRESSED, listOf())
}