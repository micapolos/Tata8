package micapolos.zexy3

class Position internal constructor(model: Any?): Value<Position>(model)

internal val Position.coords: List<Integer> get() = modelOrChildren as List<Integer>
internal val Value<Position>.position get() = this as Position

val Position.x: Integer get() = coords[0]
val Position.y: Integer get() = coords[1]

fun position(x: Int, y: Int) = position(x.value, y.value)
fun position(x: Int, y: Integer) = position(x.value, y)
fun position(x: Integer, y: Int) = position(x, y.value)
fun position(x: Integer, y: Integer) = Position(listOf(x, y))

val x = bool(false).select(position(0, 0), position(1, 0))