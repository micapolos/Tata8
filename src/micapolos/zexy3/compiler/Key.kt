package micapolos.zexy3.compiler

import micapolos.tata8.Game
import micapolos.zexy3.indexed.Key

val Key.tata
  get() = when (this) {
    Key.LEFT -> Game.keys.left
    Key.RIGHT -> Game.keys.right
    Key.UP -> Game.keys.up
    Key.DOWN -> Game.keys.down
    Key.Z -> Game.keys.z
    Key.X -> Game.keys.x
  }
