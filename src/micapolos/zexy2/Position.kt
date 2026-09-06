package micapolos.zexy2

import micapolos.zexy2.ast.Live

class Position<T>(val x: Live<T>, val y: Live<T>)

fun position(x: Double, y: Double) = Position(constant(x), constant(y))
fun <T> position(x: Live<T>, y: T) = Position(x, constant(x.kClass, y))
fun <T> position(x: T, y: Live<T>) = Position(constant(y.kClass, x), y)
fun <T> position(x: Live<T>, y: Live<T>) = Position(x, y)