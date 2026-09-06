package micapolos.zexy2

import micapolos.zexy2.ast.Expression

class Size<T>(val width: Expression<T>, val height: Expression<T>)

fun size(width: Double, height: Double) = Size(constant(width), constant(height))
fun <T> size(width: Expression<T>, height: Double) = Size(width, constant(height))
fun <T> size(width: Double, height: Expression<T>) = Size(constant(width), height)
fun <T> size(width: Expression<T>, height: Expression<T>) = Size(width, height)

val Size<Double>.center get() = Center(position(width * 0.5, height * 0.5))