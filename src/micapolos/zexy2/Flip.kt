package micapolos.zexy2

import micapolos.zexy2.ast.Live

class Flip<T>(val x: Live<T>, val y: Live<T>)

fun flip(x: Double, y: Double) = Flip(constant(x), constant(y))
fun <T> flip(x: Live<T>, y: Double) = Flip(x, constant(y))
fun <T> flip(x: Double, y: Live<T>) = Flip(constant(x), y)
fun <T> flip(x: Live<T>, y: Live<T>) = Flip(x, y)