package micapolos.zexy2

import micapolos.tata8.Game

object Screen {
  val size
    get() = size(
      Game.WIDTH.toDouble().live,
      Game.HEIGHT.toDouble().live
    )
}

val Screen.center get() = size.center