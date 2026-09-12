package micapolos.zexy3

import micapolos.zexy3.model.Key as ModelKey

class Key(internal val model: ModelKey) {
  companion object {
    val left = Key(ModelKey.LEFT)
    val right = Key(ModelKey.RIGHT)
    val up = Key(ModelKey.UP)
    val down = Key(ModelKey.DOWN)
    val z = Key(ModelKey.Z)
    val x = Key(ModelKey.X)
  }
}

val key = Key.Companion

val Key.isPressed get() = Bool(micapolos.zexy3.model.Integer.KeyDown(model))
val Key.press get() = isPressed.changeTo(true)
val Key.release get() = isPressed.changeTo(false)
