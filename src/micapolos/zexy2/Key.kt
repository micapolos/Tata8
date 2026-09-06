package micapolos.zexy2

import micapolos.tata8.Game
import micapolos.zexy2.live.Live
import micapolos.zexy2.live.Primitive

enum class Key(internal val tata8: micapolos.tata8.Key) {
  LEFT(Game.keys.left),
  RIGHT(Game.keys.right),
  UP(Game.keys.up),
  DOWN(Game.keys.down),
  Z(Game.keys.z),
  X(Game.keys.x);

  val isPressed: Live<Boolean> =
    Live.Application(
      Boolean::class,
      Primitive.KEY_IS_PRESSED,
      listOf(live(Key::class)))

  val pressed: Live<Boolean> =
    Live.Application(
      Boolean::class,
      Primitive.KEY_PRESSED,
      listOf(live(Key::class)))

  val released: Live<Boolean> =
    Live.Application(
      Boolean::class,
      Primitive.KEY_RELEASED,
      listOf(live(Key::class)))
}
