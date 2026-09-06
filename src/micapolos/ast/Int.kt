package micapolos.ast

fun constant(i: Int): Expression<Int> =
  Expression.Constant(Int::class, i)

fun variable(i: Int): Expression<Int> = variable(constant(i))

operator fun Expression<Int>.plus(i: Int): Expression<Int> = plus(constant(i))

@JvmName("plusInt")
operator fun Expression<Int>.plus(expression: Expression<Int>): Expression<Int> =
  Expression.Application(kClass, "Int.plus", listOf(this, expression))

operator fun Expression<Int>.minus(i: Int): Expression<Int> = minus(constant(i))

@JvmName("minusInt")
operator fun Expression<Int>.minus(expression: Expression<Int>): Expression<Int> =
  Expression.Application(kClass, "Int.minus", listOf(this, expression))

operator fun Expression<Int>.times(i: Int): Expression<Int> = times(constant(i))

@JvmName("timesInt")
operator fun Expression<Int>.times(expression: Expression<Int>): Expression<Int> =
  Expression.Application(kClass, "Int.times", listOf(this, expression))

fun Expression<Int>.keepAdding(i: Int): Expression<Unit> = keepAdding(constant(i))

@JvmName("keepAddingInt")
fun Expression<Int>.keepAdding(expression: Expression<Int>): Expression<Unit> =
  Expression.Application(Unit::class, "Int.keepAdding", listOf(variable, expression))

