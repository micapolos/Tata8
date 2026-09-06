package micapolos.zexy2

import micapolos.zexy2.ast.Live
import micapolos.zexy2.ast.Primitive

fun constant(d: Double): Live<Double> =
  Live.Constant(Double::class, d)

fun variable(d: Double): Live<Double> = variable(constant(d))

fun Live<Double>.keepAdding(d: Double) = keepAdding(constant(d))

@JvmName("keepAddingDouble")
fun Live<Double>.keepAdding(live: Live<Double>): Live<Animation<Double>> =
  Live.Application(Animation::class, Primitive.DOUBLE_KEEP_ADDING, listOf(variable, live))

operator fun Live<Double>.plus(i: Double): Live<Double> = plus(constant(i))

@JvmName("plusDouble")
operator fun Live<Double>.plus(live: Live<Double>): Live<Double> =
  Live.Application(kClass, Primitive.DOUBLE_PLUS, listOf(this, live))

operator fun Live<Double>.minus(d: Double): Live<Double> = minus(constant(d))

@JvmName("minusDouble")
operator fun Live<Double>.minus(live: Live<Double>): Live<Double> =
  Live.Application(kClass, Primitive.DOUBLE_MINUS, listOf(this, live))

operator fun Live<Double>.times(d: Double): Live<Double> = times(constant(d))

@JvmName("timesDouble")
operator fun Live<Double>.times(live: Live<Double>): Live<Double> =
  Live.Application(kClass, Primitive.DOUBLE_TIMES, listOf(this, live))
