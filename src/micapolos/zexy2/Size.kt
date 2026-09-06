package micapolos.zexy2

import micapolos.zexy2.ast.Live

class Size<T>(val width: Live<T>, val height: Live<T>)

fun size(width: Double, height: Double) = Size(constant(width), constant(height))
fun <T> size(width: Live<T>, height: Double) = Size(width, constant(height))
fun <T> size(width: Double, height: Live<T>) = Size(constant(width), height)
fun <T> size(width: Live<T>, height: Live<T>) = Size(width, height)

val Size<Double>.center get() = Center(position(width * 0.5, height * 0.5))