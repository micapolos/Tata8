package micapolos.zexy

class Size internal constructor(impl: Any): Value<Size>(impl)

val Value<Size>.width get() = children[0] as Value<Integer>
val Value<Size>.height get() = children[1] as Value<Integer>

fun size(width: Int, height: Int) = size(width.value, height.value)
fun size(width: Int, height: Value<Integer>) = size(width.value, height)
fun size(width: Value<Integer>, height: Int) = size(width, height.value)
fun size(width: Value<Integer>, height: Value<Integer>): Value<Size> = Size(listOf(width, height))
