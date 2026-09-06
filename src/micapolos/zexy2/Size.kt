package micapolos.zexy2

import micapolos.zexy2.live.Live

class Size<T>(val width: Live<T>, val height: Live<T>)

fun size(width: Double, height: Double) = Size(width.live, height.live)
fun <T> size(width: Live<T>, height: Double) = Size(width, height.live)
fun <T> size(width: Double, height: Live<T>) = Size(width.live, height)
fun <T> size(width: Live<T>, height: Live<T>) = Size(width, height)

val Size<Double>.center get() = Center(position(width * 0.5, height * 0.5))