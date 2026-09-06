package micapolos.zexy2

import micapolos.zexy2.ast.Live

class Anchor<T>(val x: Live<T>, val y: Live<T>)

fun anchor(x: Double, y: Double) = Anchor(constant(x), constant(y))
fun <T> anchor(x: Live<T>, y: T) = Anchor(x, constant(x.kClass, y))
fun <T> anchor(x: T, y: Live<T>) = Anchor(constant(y.kClass, x), y)
fun <T> anchor(x: Live<T>, y: Live<T>) = Anchor(x, y)

val <T> Position<T>.anchor: Anchor<T> get() = anchor(x, y)
