package micapolos.zexy3

class Size internal constructor(model: Any?): Value<Size>(model)

internal val Size.coords: List<Integer> get() = modelOrChildren as List<Integer>

val Size.width: Integer get() = coords[0]
val Size.height: Integer get() = coords[1]

fun size(width: Int, height: Int) = size(width.value, height.value)
fun size(width: Int, height: Integer) = size(width.value, height)
fun size(width: Integer, height: Int) = size(width, height.value)
fun size(width: Integer, height: Integer) = Size(listOf(width, height))
