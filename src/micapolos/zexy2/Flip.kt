package micapolos.zexy2

import micapolos.zexy2.ast.Expression

class Flip<T>(val x: Expression<T>, val y: Expression<T>)

fun flip(x: Double, y: Double) = Flip(constant(x), constant(y))
fun <T> flip(x: Expression<T>, y: Double) = Flip(x, constant(y))
fun <T> flip(x: Double, y: Expression<T>) = Flip(constant(x), y)
fun <T> flip(x: Expression<T>, y: Expression<T>) = Flip(x, y)