package micapolos.ast

import micapolos.tata8.Game

enum class Key(internal val tata8: micapolos.tata8.Key) {
  LEFT(Game.keys.left),
  RIGHT(Game.keys.right),
  UP(Game.keys.up),
  DOWN(Game.keys.down),
  Z(Game.keys.z),
  X(Game.keys.x);

  val isPressed: Expression<Boolean> =
    Expression.Application(
      Boolean::class,
      "Key.isPressed",
      listOf(constant(micapolos.ast.Key::class, this)))

  val pressed: Expression<Boolean> =
    Expression.Application(
      Boolean::class,
      "Key.pressed",
      listOf(constant(micapolos.ast.Key::class, this)))

  val released: Expression<Boolean> =
    Expression.Application(
      Boolean::class,
      "Key.released",
      listOf(constant(micapolos.ast.Key::class, this)))
}
