package micapolos.zexy2

import micapolos.zexy2.ast.Expression

class Scale<T>(val x: Expression<T>, val y: Expression<T>)

fun scale(x: Double, y: Double) = Scale(constant(x), constant(y))
fun <T> scale(x: Expression<T>, y: Double) = Scale(x, constant(y))
fun <T> scale(x: Double, y: Expression<T>) = Scale(constant(x), y)
fun <T> scale(x: Expression<T>, y: Expression<T>) = Scale(x, y)