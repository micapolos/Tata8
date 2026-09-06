package micapolos.zexy2

import micapolos.zexy2.live.Live
import micapolos.zexy2.live.Primitive
import micapolos.zexy2.live.Run

val Double.live get() = live(Double::class)

fun newVariable(d: Double): Live<Double> = newVariable(d.live)

fun Live<Double>.set(d: Double): Live<Run> = set(d.live)

fun Live<Double>.keepAdding(d: Double) = keepAdding(d.live)

@JvmName("keepAddingDouble")
fun Live<Double>.keepAdding(live: Live<Double>): Live<Run> =
  Live.Application(Run::class, Primitive.DOUBLE_KEEP_ADDING, listOf(variable, live))

operator fun Live<Double>.plus(d: Double): Live<Double> = plus(d.live)

@JvmName("plusDouble")
operator fun Live<Double>.plus(live: Live<Double>): Live<Double> =
  Live.Application(kClass, Primitive.DOUBLE_PLUS, listOf(this, live))

operator fun Live<Double>.minus(d: Double): Live<Double> = minus(d.live)

@JvmName("minusDouble")
operator fun Live<Double>.minus(live: Live<Double>): Live<Double> =
  Live.Application(kClass, Primitive.DOUBLE_MINUS, listOf(this, live))

operator fun Live<Double>.times(d: Double): Live<Double> = times(d.live)

@JvmName("timesDouble")
operator fun Live<Double>.times(live: Live<Double>): Live<Double> =
  Live.Application(kClass, Primitive.DOUBLE_TIMES, listOf(this, live))

val Live<Double>.int: Live<Int> get() =
  Live.Application(Int::class, Primitive.DOUBLE_INT, listOf(this))

val Live<Double>.fraction: Live<Double> get() =
  Live.Application(Double::class, Primitive.DOUBLE_FRACTION, listOf(this))