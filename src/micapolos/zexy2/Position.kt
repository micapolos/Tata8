package micapolos.zexy2

import micapolos.zexy2.ast.Expression

class Position<T>(val x: Expression<T>, val y: Expression<T>)

fun position(x: Double, y: Double) = Position(constant(x), constant(y))
fun <T> position(x: Expression<T>, y: Double) = Position(x, constant(y))
fun <T> position(x: Double, y: Expression<T>) = Position(constant(x), y)
fun <T> position(x: Expression<T>, y: Expression<T>) = Position(x, y)