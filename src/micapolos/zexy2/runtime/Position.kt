package micapolos.zexy2.runtime

class Position<T: Number>(val x: Value<T>, val y: Value<T>)

fun position(x: Double, y: Double) = Position(x.value, y.value)
fun position(x: Double, y: Value<Double>) = Position(x.value, y)
fun position(x: Value<Double>, y: Double) = Position(x, y.value)
fun position(x: Value<Double>, y: Value<Double>) = Position(x, y)
