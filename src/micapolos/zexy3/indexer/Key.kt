package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Key
import micapolos.zexy3.model.Key as ModelKey

fun Indexer.indexed(model: ModelKey): Key =
  when (model) {
    ModelKey.LEFT -> Key.LEFT
    ModelKey.RIGHT -> Key.RIGHT
    ModelKey.UP -> Key.UP
    ModelKey.DOWN -> Key.DOWN
    ModelKey.Z -> Key.Z
    ModelKey.X -> Key.X
  }