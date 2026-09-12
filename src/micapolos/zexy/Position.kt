package micapolos.zexy

class Position internal constructor(model: Any?) : Value<Position>(model)

internal val Position.coords: List<Integer> get() = modelOrChildren as List<Integer>
internal val Value<Position>.position get() = this as Position

val Value<Position>.x get() = children[0] as Value<Integer>
val Value<Position>.y get() = children[1] as Value<Integer>

fun position(x: Int, y: Int) = position(x.value, y.value)
fun position(x: Int, y: Value<Integer>) = position(x.value, y)
fun position(x: Value<Integer>, y: Int) = position(x, y.value)
fun position(x: Value<Integer>, y: Value<Integer>) = Position(listOf(x, y))
