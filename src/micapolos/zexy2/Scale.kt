package micapolos.zexy2

import micapolos.zexy2.ast.Live

class Scale<T>(val x: Live<T>, val y: Live<T>)

fun scale(x: Double, y: Double) = Scale(constant(x), constant(y))
fun <T> scale(x: Live<T>, y: Double) = Scale(x, constant(y))
fun <T> scale(x: Double, y: Live<T>) = Scale(constant(x), y)
fun <T> scale(x: Live<T>, y: Live<T>) = Scale(x, y)