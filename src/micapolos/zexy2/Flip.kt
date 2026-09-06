package micapolos.zexy2

import micapolos.zexy2.live.Live

class Flip<T>(val x: Live<T>, val y: Live<T>)

fun flip(x: Double, y: Double) = Flip(x.live, y.live)
fun <T> flip(x: Live<T>, y: Double) = Flip(x, y.live)
fun <T> flip(x: Double, y: Live<T>) = Flip(x.live, y)
fun <T> flip(x: Live<T>, y: Live<T>) = Flip(x, y)