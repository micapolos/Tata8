package micapolos.zexy2

import micapolos.zexy2.ast.Live

object Mouse {
  val position =
    Position<Double>(
      Live.Application(Double::class, "Mouse.position.x", listOf()),
      Live.Application(Double::class, "Mouse.position.y", listOf()))

  val isPressed =
    Live.Application<Boolean>(Boolean::class, "Mouse.button.isPressed", listOf())

  val pressed =
    Live.Application<Boolean>(Boolean::class, "Mouse.button.pressed", listOf())
}