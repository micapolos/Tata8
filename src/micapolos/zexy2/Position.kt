package micapolos.zexy2

import micapolos.zexy2.live.Live

class Position<T>(val x: Live<T>, val y: Live<T>)

fun position(x: Double, y: Double) = Position(x.live, y.live)
fun <T> position(x: Live<T>, y: T) = Position(x, y.live(x.kClass))
fun <T> position(x: T, y: Live<T>) = Position(x.live(y.kClass), y)
fun <T> position(x: Live<T>, y: Live<T>) = Position(x, y)