package micapolos.zexy2

import micapolos.tata8.Game

object Screen

val Screen.size get() = size(
    constant(Game.WIDTH.toDouble()),
    constant(Game.HEIGHT.toDouble()))

val Screen.center get() = size.center