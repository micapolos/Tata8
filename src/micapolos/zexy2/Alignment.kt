package micapolos.zexy2

import micapolos.zexy2.live.Live

class Alignment<T>(val x: Live<T>, val y: Live<T>)

fun alignment(x: Double, y: Double) = Alignment(x.live, y.live)
fun <T> alignment(x: Live<T>, y: T) = Alignment(x, y.live(x.kClass))
fun <T> alignment(x: T, y: Live<T>) = Alignment(x.live(y.kClass), y)
fun <T> alignment(x: Live<T>, y: Live<T>) = Alignment(x, y)

val leftTopAlignment = alignment(0.0, 0.0)
val centerAlignment = alignment(0.5, 0.5)
val centerTopAlignment = alignment(0.5, 0.0)