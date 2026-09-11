package micapolos.zexy3

import micapolos.zexy3.model.Integer.KeyDown as ModelKeyDown
import micapolos.zexy3.model.Key as ModelKey

class Key internal constructor(isPressed: Value<Bool>): Value<Key>(listOf(isPressed)) {
  companion object {
    val left = key(ModelKey.LEFT)
    val right = key(ModelKey.RIGHT)
    val up = key(ModelKey.UP)
    val down = key(ModelKey.DOWN)
    val z = key(ModelKey.Z)
    val x = key(ModelKey.X)

    internal fun key(model: ModelKey) = key(Bool(Integer(ModelKeyDown(model))))
  }
}

val Value<Key>.isPressed get() = children[0] as Value<Bool>

fun key(isPressed: Value<Bool>): Value<Key> = Key(isPressed)

val key = Key.Companion

val Value<Key>.pressed: Event get() = TODO()
val Value<Key>.released: Event get() = TODO()
