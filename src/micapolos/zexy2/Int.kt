package micapolos.zexy2

import micapolos.zexy2.live.Live
import micapolos.zexy2.live.Primitive

val Int.live get() = live(Int::class)

fun newVariable(i: Int): Live<Int> = newVariable(i.live)

operator fun Live<Int>.plus(i: Int): Live<Int> = plus(i.live)

fun Live<Int>.set(d: Int): Live<Unit> =
  set(d.live)

@JvmName("plusInt")
operator fun Live<Int>.plus(live: Live<Int>): Live<Int> =
  Live.Application(kClass, Primitive.INT_PLUS, listOf(this, live))

operator fun Live<Int>.minus(i: Int): Live<Int> = minus(i.live)

@JvmName("minusInt")
operator fun Live<Int>.minus(live: Live<Int>): Live<Int> =
  Live.Application(kClass, Primitive.INT_MINUS, listOf(this, live))

operator fun Live<Int>.times(i: Int): Live<Int> = times(i.live)

@JvmName("timesInt")
operator fun Live<Int>.times(live: Live<Int>): Live<Int> =
  Live.Application(kClass, Primitive.INT_TIMES, listOf(this, live))

fun Live<Int>.keepAdding(i: Int): Live<Unit> = keepAdding(i.live)

@JvmName("keepAddingInt")
fun Live<Int>.keepAdding(live: Live<Int>): Live<Unit> =
  Live.Application(Unit::class, Primitive.INT_KEEP_ADDING, listOf(variable, live))

