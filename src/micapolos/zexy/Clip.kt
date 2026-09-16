package micapolos.zexy

class Clip internal constructor(position: Value<Position>, size: Value<Size>) : ValueWithChildren<Clip>(position, size)

val Value<Clip>.position  get() = children[0] as Value<Position>
val Value<Clip>.size get() = children[1] as Value<Size>

fun clip(position: Value<Position>, size: Value<Size>) = Clip(position, size)
