package micapolos.zexy2

import micapolos.zexy2.live.Live
import micapolos.zexy2.live.Primitive

val Double.live get() = live(Double::class)

fun newVariable(d: Double): Live<Double> = newVariable(d.live)

fun Live<Double>.set(d: Double): Live<Unit> = set(d.live)

fun withVariable(initial: Double, fn: (Live<Double>) -> Live<Unit>) =
  withVariable(initial.live, fn)

fun Live<Double>.keepAdding(d: Double) = keepAdding(d.live)

@JvmName("keepAddingDouble")
fun Live<Double>.keepAdding(live: Live<Double>): Live<Unit> =
  set(this + frameTime * live)

operator fun Live<Double>.unaryMinus(): Live<Double> = 0.0.live - this

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

@JvmName("timesInt")
operator fun Live<Double>.times(i: Int): Live<Double> = times(i.live)

@JvmName("timesInt")
operator fun Live<Double>.times(live: Live<Int>): Live<Double> = times(live.double)

val Live<Double>.int: Live<Int> get() =
  Live.Application(Int::class, Primitive.DOUBLE_INT, listOf(this))

val Live<Double>.fraction: Live<Double> get() =
  Live.Application(Double::class, Primitive.DOUBLE_FRACTION, listOf(this))

val frameTime: Live<Double> get() =
  Live.Application(Double::class, Primitive.FRAME_TIME, listOf())

fun Live<Double>.add(d: Double) = add(d.live)
fun Live<Double>.add(d: Live<Double>) = set(this + d)

fun Live<Double>.subtract(d: Double) = subtract(d.live)
fun Live<Double>.subtract(d: Live<Double>) = set(this - d)

fun Live<Double>.multiply(d: Double) = multiply(d.live)
fun Live<Double>.multiply(d: Live<Double>) = set(this + d)

val Live<Double>.elastic get() = Live.Elastic(this)
