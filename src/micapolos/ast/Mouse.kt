package micapolos.ast

object Mouse {
  val position =
    Position(
      Expression.Application(Double::class, "Mouse.position.x", listOf()),
      Expression.Application(Double::class, "Mouse.position.y", listOf()))

  val isPressed =
    Expression.Application<Boolean>(Boolean::class, "Mouse.button.isPressed", listOf())

  val pressed =
    Expression.Application<Boolean>(Boolean::class, "Mouse.button.pressed", listOf())
}