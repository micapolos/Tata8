package micapolos.zexy2

import micapolos.zexy2.ast.Expression

class Size<T>(val x: Expression<T>, val y: Expression<T>)

fun size(x: Double, y: Double) = Size(constant(x), constant(y))
fun <T> size(x: Expression<T>, y: Double) = Size(x, constant(y))
fun <T> size(x: Double, y: Expression<T>) = Size(constant(x), y)
fun <T> size(x: Expression<T>, y: Expression<T>) = Size(x, y)