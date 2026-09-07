package micapolos.zexy2

import micapolos.zexy2.live.Live
import micapolos.zexy2.live.Primitive
import micapolos.zexy2.live.liveApplication

val Int.live get() = live(Int::class)

fun newVariable(i: Int): Live<Int> = newVariable(i.live)

operator fun Live<Int>.plus(i: Int): Live<Int> = plus(i.live)

fun Live<Int>.set(d: Int): Live<Unit> = set(d.live)

operator fun Live<Int>.unaryMinus(): Live<Int> = 0.live - this

@JvmName("plusInt")
operator fun Live<Int>.plus(live: Live<Int>) =
  liveApplication<Int>(kClass, Primitive.INT_PLUS, this, live)

operator fun Live<Int>.minus(i: Int): Live<Int> = minus(i.live)

@JvmName("minusInt")
operator fun Live<Int>.minus(live: Live<Int>) =
  liveApplication<Int>(kClass, Primitive.INT_MINUS, this, live)

operator fun Live<Int>.times(i: Int): Live<Int> = times(i.live)

@JvmName("timesInt")
operator fun Live<Int>.times(live: Live<Int>) =
  liveApplication<Int>(kClass, Primitive.INT_TIMES, this, live)

fun Live<Int>.keepAdding(i: Int): Live<Animation> = keepAdding(i.live)

@JvmName("keepAddingInt")
fun Live<Int>.keepAdding(live: Live<Int>): Live<Animation> =
  Live.Application(Animation::class, Primitive.INT_KEEP_ADDING, listOf(variable, live))

val Live<Int>.double: Live<Double> get() =
  Live.Application(Double::class, Primitive.INT_DOUBLE, listOf(this))

fun Live<Int>.floorMod(int: Int) = floorMod(int.live)

fun Live<Int>.floorMod(int: Live<Int>): Live<Int> =
  Live.Application(Int::class, Primitive.INT_FLOOR_MOD, listOf(this, int))