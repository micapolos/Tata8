package micapolos.zexy2

import micapolos.zexy2.ast.Live

fun constant(i: Int): Live<Int> =
  Live.Constant(Int::class, i)

fun variable(i: Int): Live<Int> = variable(constant(i))

operator fun Live<Int>.plus(i: Int): Live<Int> = plus(constant(i))

@JvmName("plusInt")
operator fun Live<Int>.plus(live: Live<Int>): Live<Int> =
  Live.Application(kClass, "Int.plus", listOf(this, live))

operator fun Live<Int>.minus(i: Int): Live<Int> = minus(constant(i))

@JvmName("minusInt")
operator fun Live<Int>.minus(live: Live<Int>): Live<Int> =
  Live.Application(kClass, "Int.minus", listOf(this, live))

operator fun Live<Int>.times(i: Int): Live<Int> = times(constant(i))

@JvmName("timesInt")
operator fun Live<Int>.times(live: Live<Int>): Live<Int> =
  Live.Application(kClass, "Int.times", listOf(this, live))

fun Live<Int>.keepAdding(i: Int): Live<Unit> = keepAdding(constant(i))

@JvmName("keepAddingInt")
fun Live<Int>.keepAdding(live: Live<Int>): Live<Unit> =
  Live.Application(Unit::class, "Int.keepAdding", listOf(variable, live))

