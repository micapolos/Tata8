package micapolos.zexy3

class Position internal constructor(model: Any?): Value<Position>(model)

internal val Position.coords: List<Integer> get() = modelOrChildren as List<Integer>
internal val Value<Position>.position get() = this as Position

val Position.x: Value<Integer> get() = coords[0]
val Position.y: Value<Integer> get() = coords[1]

fun position(x: Int, y: Int) = position(x.value, y.value)
fun position(x: Int, y: Value<Integer>) = position(x.value, y)
fun position(x: Value<Integer>, y: Int) = position(x, y.value)
fun position(x: Value<Integer>, y: Value<Integer>) = Position(listOf(x, y))
