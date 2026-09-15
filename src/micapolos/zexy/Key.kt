package micapolos.zexy

import micapolos.zexy.model.Key as ModelKey

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

val Key.isPressed get() = Bool(micapolos.zexy.model.Integer.KeyDown(model))

context(_: Animation.Block)
val Key.press get() = isPressed.changeTo(true)

context(_: Animation.Block)
val Key.release get() = isPressed.changeTo(false)
