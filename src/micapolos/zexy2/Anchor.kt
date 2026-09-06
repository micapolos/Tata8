package micapolos.zexy2

import micapolos.zexy2.live.Live

class Anchor<T>(val x: Live<T>, val y: Live<T>)

fun anchor(x: Double, y: Double) = Anchor(x.live, y.live)
fun <T> anchor(x: Live<T>, y: T) = Anchor(x, y.live(x.kClass))
fun <T> anchor(x: T, y: Live<T>) = Anchor(x.live(y.kClass), y)
fun <T> anchor(x: Live<T>, y: Live<T>) = Anchor(x, y)

val <T> Position<T>.anchor: Anchor<T> get() = anchor(x, y)
