package micapolos.zexy2

import micapolos.tata8.Image
import micapolos.zexy2.ast.Expression

class Anchor<T>(val x: Expression<T>, val y: Expression<T>)

fun anchor(x: Double, y: Double) = Anchor(constant(x), constant(y))
fun <T> anchor(x: Expression<T>, y: T) = Anchor(x, constant(x.kClass, y))
fun <T> anchor(x: T, y: Expression<T>) = Anchor(constant(y.kClass, x), y)
fun <T> anchor(x: Expression<T>, y: Expression<T>) = Anchor(x, y)

val Image.centerAnchor: Anchor<Double> get() =
  anchor(size.width.toDouble() * 0.5, size.height.toDouble() * 0.5)