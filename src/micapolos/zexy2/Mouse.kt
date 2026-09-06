package micapolos.zexy2

import micapolos.zexy2.ast.Expression

object Mouse {
  val position =
    Position<Double>(
      Expression.Application(Double::class, "Mouse.position.x", listOf()),
      Expression.Application(Double::class, "Mouse.position.y", listOf()))

  val isPressed =
    Expression.Application<Boolean>(Boolean::class, "Mouse.button.isPressed", listOf())

  val pressed =
    Expression.Application<Boolean>(Boolean::class, "Mouse.button.pressed", listOf())
}